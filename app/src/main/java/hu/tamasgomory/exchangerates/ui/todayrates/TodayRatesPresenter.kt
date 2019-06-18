package hu.tamasgomory.exchangerates.ui.todayrates

import hu.tamasgomory.exchangerates.base.BasePresenter
import javax.inject.Inject
import android.util.Log
import hu.tamasgomory.exchangerates.base.IInteractor
import hu.tamasgomory.exchangerates.base.IPresenter
import hu.tamasgomory.exchangerates.base.IView

class TodayRatesPresenter
    @Inject
    constructor(view: TodayRatesContract.View, interactor: TodayRatesContract.Interactor):
        BasePresenter<TodayRatesContract.View, TodayRatesContract.Interactor>(view, interactor),
        TodayRatesContract.Presenter
{
    override fun onCreate() {
        super<BasePresenter>.onCreate()
        view.showAmount(1.0)
        Log.d("TodayRatesPresenter", "onCreate")
    }

    override fun showSelectedCurrency(currencyCode: String) {
        view.showSelectedCurrency(currencyCode)
    }

    override fun onAmountChanged(amount: Double) {
        interactor.onAmountChanged(amount)
    }
}