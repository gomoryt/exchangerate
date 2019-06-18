package hu.tamasgomory.exchangerates.ui.todayrates

import hu.tamasgomory.exchangerates.base.IInteractor
import hu.tamasgomory.exchangerates.base.IPresenter
import hu.tamasgomory.exchangerates.base.IView

interface TodayRatesContract {
    interface View : IView {
        fun showSelectedCurrency(currencyCode: String)
        fun showAmount(amount: Double)
        fun displayError()
        fun showExchangeRateResult(rates: List<TodayRateListItemModel>)
        var availableCurrencies: MutableList<String>
    }
    interface Presenter: IPresenter {
        fun showSelectedCurrency(currencyCode: String)
        fun showExchangeRates(baseCurrency: String, amount: Double, rates: HashMap<String, Double>)
        fun onAmountChanged(amount: Double)
        fun displayError()
        fun onCurrencyChanged(currencyCode: String)
    }
    interface Interactor: IInteractor<Presenter> {
        fun onAmountChanged(amount: Double)
        fun onCurrencyChanged(currencyCode: String)
        fun fetchLatestExchangeRates(baseCurrency: String)
    }
}