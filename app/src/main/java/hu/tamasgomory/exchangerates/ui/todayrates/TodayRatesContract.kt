package hu.tamasgomory.exchangerates.ui.todayrates

import hu.tamasgomory.exchangerates.base.IInteractor
import hu.tamasgomory.exchangerates.base.IPresenter
import hu.tamasgomory.exchangerates.base.IRouter
import hu.tamasgomory.exchangerates.base.IView
import hu.tamasgomory.exchangerates.base.listener.OnCurrencySelectedListener
import hu.tamasgomory.exchangerates.ui.todayrates.resultlist.TodayRateListItemModel

interface TodayRatesContract {
    interface View : IView {
        fun showSelectedCurrency(currencyCode: String)
        fun showAmount(amount: Double)
        fun showError()
        fun showLoading()
        fun showExchangeRateResult(rates: List<TodayRateListItemModel>)
    }
    interface Presenter: IPresenter, OnCurrencySelectedListener {
        fun onBasedCurrencyChanged(currencyCode: String)
        fun exchangeRatesResultReceived(baseCurrency: String, amount: Double, rates: HashMap<String, Double>)
        fun onAmountChanged(amount: Double)
        fun showError()
        fun showLoading()
        fun onCurrencyCodeClicked()
    }
    interface Interactor: IInteractor<Presenter> {
        fun onAmountChanged(amount: Double)
        fun fetchLatestExchangeRates(baseCurrency: String)
    }

    interface Router: IRouter {
        fun openBaseCurrencySelectorDialog()
        fun startRatesHistoryScreen(targetCurrency: String)
    }

}