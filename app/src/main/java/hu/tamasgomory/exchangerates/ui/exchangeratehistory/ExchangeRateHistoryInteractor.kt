package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import hu.tamasgomory.exchangerates.base.BaseInteractor
import hu.tamasgomory.exchangerates.data.ExchangeRateCalculatorModel
import hu.tamasgomory.exchangerates.data.api.ExchangeRatesApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import timber.log.Timber
import javax.inject.Inject

class ExchangeRateHistoryInteractor
    @Inject
    constructor(
            val calculatorModel: ExchangeRateCalculatorModel,
            val apiService: ExchangeRatesApiService
    ):
    BaseInteractor<ExchangeRateHistoryContract.Presenter>(),
    ExchangeRateHistoryContract.Interactor
{


    override fun fetchExchangeRateHistory(targetCurrency: String) {
        presenter.showLoading()
        val today = DateTime.now()
        val oneWeekAgo = today.minusWeeks(1)
        compositeDisposable.add(
                        calculatorModel.selectedBaseCurrency
                        .flatMapSingle {
                            apiService.exchangeRatesHistory(
                                    it,
                                    oneWeekAgo.toString("yyyy-MM-dd"),
                                    today.toString("yyyy-MM-dd"),
                                    targetCurrency
                            )
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onNext = {
                                    Timber.d("History response received")
                                    val amount = calculatorModel.amount.value?:1.0
                                    presenter.onExchangeRatesHistoryResponseReceived(it, amount)
                                },
                                onError = {
                                    Timber.e(it, "An error occured on fetch exchange rate history")
                                    presenter.showError()
                                }

                        )
        )
    }
}