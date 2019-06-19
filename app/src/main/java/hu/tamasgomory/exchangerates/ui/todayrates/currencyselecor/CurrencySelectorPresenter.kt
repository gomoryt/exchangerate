package hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor

import android.util.Log
import hu.tamasgomory.exchangerates.base.BasePresenter
import javax.inject.Inject

class CurrencySelectorPresenter
    @Inject
    constructor(view: CurrencySelectorContract.View, interactor: CurrencySelectorContract.Interactor):
    BasePresenter<CurrencySelectorContract.View, CurrencySelectorContract.Router, CurrencySelectorContract.Interactor>(
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