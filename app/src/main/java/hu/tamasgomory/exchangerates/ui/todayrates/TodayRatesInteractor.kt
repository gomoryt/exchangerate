package hu.tamasgomory.exchangerates.ui.todayrates

import android.util.Log
import javax.inject.Inject
import hu.tamasgomory.exchangerates.base.BaseInteractor
import hu.tamasgomory.exchangerates.data.ExchangeRateCalculatorModel
import hu.tamasgomory.exchangerates.data.api.ExchangeRatesApiService
import hu.tamasgomory.exchangerates.service.CurrencyService
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

//import kotlin.collections.HashMap

class TodayRatesInteractor
    @Inject constructor(
            val currencyService: CurrencyService,
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

    private fun chooseBaseCurrency() {
        if (!calculatorModel.selectedBaseCurrency.hasValue()) {
            val defaultBaseCurrency = currencyService.currencyCodeBasedOnNetwork()
            calculatorModel.selectedBaseCurrency.onNext(defaultBaseCurrency)
        }
    }

    private fun subscribeToBaseCurrencyChange(): Disposable {
        return calculatorModel.selectedBaseCurrency
                .subscribeOn(Schedulers.computation())
                .subscribeBy(
                        onNext = {
                            fetchLatestExchangeRates(it)
                        },
                        onError = {
                            Log.e("TodayRatesInteractor", it.message)
                        }
                )
    }

    private fun subscribeToCalculatedRatesResult(): Disposable {
        return Observable.combineLatest(
                    calculatorModel.selectedBaseCurrency,
                    calculatorModel.amount
                            .toFlowable(BackpressureStrategy.LATEST)
                            .throttleWithTimeout(300, TimeUnit.MILLISECONDS)
                            .toObservable(),
                    calculatorModel.rates,
                    Function3 {
                        currency: String, amount: Double, rates: HashMap<String, Double> ->
                        Triple(currency, amount, rates)
                    }
                )
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {

                        presenter.exchangeRatesResultReceived(it.first, it.second, it.third)
                        Log.d("TodayRatesInteractor", "Today rates results updated")
                    },
                    onError = {
                        Log.e("TodayRatesInteractor", it.message)
                    }
                )
    }


    override fun onAmountChanged(amount: Double) {
        calculatorModel.amount.onNext(amount)
    }

    override fun fetchLatestExchangeRates(baseCurrency: String) {
        compositeDisposable.add(apiService.latestExchangeRates(baseCurrency)
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
                            Log.w("TodayRatesInteractor", it)
                            if (it is HttpException && it.code() == 400 && baseCurrency.isNotBlank()) {
                                fetchLatestExchangeRates("")
                            }
                        }
                ))
    }
}