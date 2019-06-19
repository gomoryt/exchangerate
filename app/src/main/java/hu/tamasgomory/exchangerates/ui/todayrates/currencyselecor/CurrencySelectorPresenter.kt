package hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor

import hu.tamasgomory.exchangerates.base.BasePresenter
import hu.tamasgomory.exchangerates.base.IRouter
import javax.inject.Inject

class CurrencySelectorPresenter
    @Inject
    constructor(
            view: CurrencySelectorContract.View,
            interactor: CurrencySelectorContract.Interactor
    ):
    BasePresenter<CurrencySelectorContract.View, IRouter, CurrencySelectorContract.Interactor>(
            view,
            null,
            interactor
    ),
    CurrencySelectorContract.Presenter
{
    override fun availableCurrenciesReceived(currencies: List<String>) {
        view.showCurrencies(currencies)
    }

    override fun onCurrencySelected(currency: String) {
        interactor!!.changeBaseCurrency(currency)
        view.dismiss()
    }
}