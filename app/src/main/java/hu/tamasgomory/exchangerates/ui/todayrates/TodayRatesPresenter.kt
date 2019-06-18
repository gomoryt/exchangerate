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
        view.availableCurrencies = rates.keys.toMutableList()
        view.showSelectedCurrency(baseCurrency)

        rates.remove(baseCurrency)
        val multipliedRates = rates.map { entry ->
            TodayRateListItemModel(entry.key, entry.value * amount)
        }

        view.showExchangeRateResult(multipliedRates)

    }

    override fun onCurrencyChanged(currencyCode: String) {
        interactor.onCurrencyChanged(currencyCode)
    }

    override fun displayError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}