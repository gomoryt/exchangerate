package hu.tamasgomory.exchangerates.ui.todayrates

import hu.tamasgomory.exchangerates.base.BasePresenter
import hu.tamasgomory.exchangerates.ui.todayrates.resultlist.TodayRateListItemModel
import javax.inject.Inject

class TodayRatesPresenter
    @Inject
    constructor(view: TodayRatesContract.View, router: TodayRatesContract.Router, interactor: TodayRatesContract.Interactor):
        BasePresenter<TodayRatesContract.View, TodayRatesContract.Router, TodayRatesContract.Interactor>(
                view,
                router,
                interactor
        ),
        TodayRatesContract.Presenter
{
    override fun onCreate() {
        super<BasePresenter>.onCreate()
        view.showAmount(1.0)
    }

    override fun onSelectedCurrencyChanged(currencyCode: String) {
        view.showSelectedCurrency(currencyCode)
    }

    override fun onAmountChanged(amount: Double) {
        interactor!!.onAmountChanged(amount)
    }

    override fun exchangeRatesResultReceived(baseCurrency: String, amount: Double, rates: HashMap<String, Double>) {
        view.showSelectedCurrency(baseCurrency)

        rates.remove(baseCurrency)
        val multipliedRates = rates.map { entry ->
            TodayRateListItemModel(entry.key, entry.value * amount)
        }

        view.showExchangeRateResult(multipliedRates)

    }

    override fun onCurrencyCodeClicked() {
        router!!.openBaseCurrencySelectorDialog()
    }

    override fun displayError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}