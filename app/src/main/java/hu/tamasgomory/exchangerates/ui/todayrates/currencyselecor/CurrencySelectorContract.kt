package hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor

import hu.tamasgomory.exchangerates.base.IInteractor
import hu.tamasgomory.exchangerates.base.IPresenter
import hu.tamasgomory.exchangerates.base.IView
import hu.tamasgomory.exchangerates.base.listener.OnCurrencySelectedListener

interface CurrencySelectorContract {
    interface View: IView {
        fun showCurrencies(currencies: List<String>)
        fun dismiss()
    }
    interface Presenter: IPresenter, OnCurrencySelectedListener {
        fun availableCurrenciesReceived(currencies: List<String>)

    }
    interface Interactor: IInteractor<Presenter> {
        fun changeBaseCurrency(currency: String)
    }


}