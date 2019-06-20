package hu.tamasgomory.exchangerates.ui.todayrates

import javax.inject.Inject
import hu.tamasgomory.exchangerates.base.BaseInteractor
import hu.tamasgomory.exchangerates.data.ExchangeRateCalculatorModel
import hu.tamasgomory.exchangerates.data.api.ExchangeRatesApiService
import hu.tamasgomory.exchangerates.data.api.response.TodayRatesResponse
import hu.tamasgomory.exchangerates.util.CurrencyUtil
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import timber.log.Timber
import java.util.concurrent.TimeUnit


class TodayRatesInteractor
    @Inject constructor(
            val currencyUtil: CurrencyUtil,
            val calculatorModel: ExchangeRateCalculatorModel,
            val apiService: ExchangeRatesApiService
    ):
        BaseInteractor<TodayRatesContract.Presenter>(),
        TodayRatesContract.Interactor
{

    override fun init() {

        compositeDisposable.addAll(
            subscribeToBaseCurrencyChange(),
            subscribeToCalculatedRatesResult()

        )

        chooseBaseCurrency()

    }

    internal fun chooseBaseCurrency() {
        if (!calculatorModel.selectedBaseCurrency.hasValue()) {
            val defaultBaseCurrency = currencyUtil.currencyCodeBasedOnNetwork()
            calculatorModel.selectedBaseCurrency.onNext(defaultBaseCurrency)
        }
    }

    internal fun subscribeToBaseCurrencyChange(): Disposable {
        return calculatorModel.selectedBaseCurrency
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            fetchLatestExchangeRates(it)
                        },
                        onError = {
                            Timber.e(it.message)
                        }
                )
    }

    internal fun subscribeToCalculatedRatesResult(): Disposable {
        return Observable.combineLatest(
                    calculatorModel.amount
                            .toFlowable(BackpressureStrategy.LATEST)
                            .throttleWithTimeout(300, TimeUnit.MILLISECONDS)
                            .toObservable(),
                    calculatorModel.rates,
                    BiFunction {
                        amount: Double, rates: HashMap<String, Double> ->
                        Pair(amount, rates)
                    }
                )
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        Timber.d("Today rates results updated")
                        presenter.exchangeRatesResultReceived(
                                calculatorModel.selectedBaseCurrency.value!!,
                                it.first,
                                it.second
                        )
                    },
                    onError = {
                        Timber.e(it)
                    }
                )
    }


    override fun onAmountChanged(amount: Double) {
        calculatorModel.amount.onNext(amount)
    }

    override fun fetchLatestExchangeRates(baseCurrency: String) {
        presenter.showLoading()
        compositeDisposable.add(
                Single.zip(
                        apiService.latestExchangeRates(baseCurrency),
                        Single.timer(500, TimeUnit.MILLISECONDS), // Loading layout atleast 500 ms
                        BiFunction<TodayRatesResponse, Long, TodayRatesResponse> { response, notUsed ->
                            response
                        }
                    )
                    .subscribeOn(Schedulers.io())
                    .subscribeBy (
                            onSuccess = {todayRatesResponse ->
                                calculatorModel.rates.onNext(todayRatesResponse.rates)
                                if (
                                        calculatorModel.selectedBaseCurrency.hasValue() &&
                                        calculatorModel.selectedBaseCurrency.value != todayRatesResponse.base
                                ) {
                                    calculatorModel.selectedBaseCurrency.onNext(todayRatesResponse.base)
                                }
                            },
                            onError = {
                                Timber.w(it, "Error occured on fetch latest exchange rate")
                                if (it is HttpException && it.code() == 400 && baseCurrency.isNotBlank()) {
                                    fetchLatestExchangeRates("")
                                } else {
                                    presenter.showError()
                                }
                            }
                    )
        )
    }
}