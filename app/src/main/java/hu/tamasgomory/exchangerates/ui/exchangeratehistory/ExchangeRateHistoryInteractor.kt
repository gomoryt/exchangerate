package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import android.util.Log
import hu.tamasgomory.exchangerates.base.BaseInteractor
import hu.tamasgomory.exchangerates.data.ExchangeRateCalculatorModel
import hu.tamasgomory.exchangerates.data.api.ExchangeRatesApiService
import hu.tamasgomory.exchangerates.data.api.response.ExchangeRateHistoryResponse
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
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
                                    Log.d("HistoryInteractor", "Response")
                                    val amount = calculatorModel.amount.value?:1.0
                                    presenter.onExchangeRatesHistoryResponseReceived(it, amount)
                                },
                                onError = {
                                    Log.d("HistoryInteractor", it.message)
                                    presenter.showError()
                                }

                        )
        )
    }
}