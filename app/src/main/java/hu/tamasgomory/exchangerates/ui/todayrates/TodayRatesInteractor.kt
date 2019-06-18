package hu.tamasgomory.exchangerates.ui.todayrates

import javax.inject.Inject
import hu.tamasgomory.exchangerates.base.BaseInteractor
import hu.tamasgomory.exchangerates.data.ExchangeRateCalculatorModel
import hu.tamasgomory.exchangerates.service.CurrencyService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TodayRatesInteractor
    @Inject constructor(
            val currencyService: CurrencyService,
            val calculatorModel: ExchangeRateCalculatorModel
    ):
        BaseInteractor<TodayRatesContract.Presenter>(),
        TodayRatesContract.Interactor
{

    override fun init() {
        calculatorModel.selectedBaseCurrency.onNext(currencyService.currencyCodeBasedOnNetwork())

        compositeDisposable.addAll(
            calculatorModel.selectedBaseCurrency
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    presenter.showSelectedCurrency(it)
                }
        )

    }

    override fun onAmountChanged(amount: Double) {
        calculatorModel.amount.onNext(amount)
    }
}