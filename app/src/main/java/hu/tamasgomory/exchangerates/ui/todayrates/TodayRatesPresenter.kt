package hu.tamasgomory.exchangerates.ui.todayrates

import hu.tamasgomory.exchangerates.base.BasePresenter
import hu.tamasgomory.exchangerates.ui.todayrates.resultlist.TodayRateListItemModel
import hu.tamasgomory.exchangerates.util.CurrencyUtil
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class TodayRatesPresenter
    @Inject
    constructor(
            view: TodayRatesContract.View,
            router: TodayRatesContract.Router,
            interactor: TodayRatesContract.Interactor,
            val currencyUtil: CurrencyUtil
    ):
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

    override fun onBasedCurrencyChanged(currencyCode: String) {
        view.showSelectedCurrency(currencyCode)
    }

    override fun onAmountChanged(amount: Double) {
        interactor!!.onAmountChanged(amount)
    }

    override fun exchangeRatesResultReceived(baseCurrency: String, amount: Double, rates: HashMap<String, Double>) {
        view.showSelectedCurrency(baseCurrency)

        rates.remove(baseCurrency)
        val multipliedRates = rates.map { entry ->
            val currency = Currency.getInstance(entry.key)
            val rate = currencyUtil.roundCurrency(currency, entry.value * amount)
            TodayRateListItemModel(entry.key, "%s %s". format(rate, currency.symbol))
        }

        view.showExchangeRateResult(multipliedRates)

    }

    override fun onCurrencyCodeClicked() {
        router!!.openBaseCurrencySelectorDialog()
    }

    override fun onCurrencySelected(currency: String) {
        router!!.startRatesHistoryScreen(currency)
    }

    override fun showError() {
        view.showError()
    }

    override fun showLoading() {
        view.showLoading()
    }
}