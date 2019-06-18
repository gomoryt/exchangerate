package hu.tamasgomory.exchangerates.ui.todayrates

import hu.tamasgomory.exchangerates.base.BasePresenter
import javax.inject.Inject
import android.util.Log

class TodayRatesPresenter
    @Inject
    constructor(view: TodayRatesContract.View, interactor: TodayRatesContract.Interactor):
        BasePresenter<TodayRatesContract.View, TodayRatesContract.Interactor>(view, interactor),
        TodayRatesContract.Presenter
{
    override fun onCreate() {
        super<BasePresenter>.onCreate()
        view.showAmount(1.0)
    }

    override fun showSelectedCurrency(currencyCode: String) {
        view.showSelectedCurrency(currencyCode)
    }

    override fun onAmountChanged(amount: Double) {
        interactor.onAmountChanged(amount)
    }

    override fun showExchangeRates(baseCurrency: String, amount: Double, rates: HashMap<String, Double>) {
        rates.remove(baseCurrency)
        val multipliedRates = rates.map { entry ->
            entry.key to entry.value * amount
        }.toMap()

        Log.d("TodayRatesPresenter", multipliedRates.toString())

    }

    override fun displayError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}