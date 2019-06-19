package hu.tamasgomory.exchangerates.ui.todayrates

import hu.tamasgomory.exchangerates.ui.exchangeratehistory.ExchangeRateHistoryActivity
import hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor.CurrencySelectorDialogFragment
import javax.inject.Inject

class TodayRatesRouter
    @Inject
    constructor(val view: TodayRatesContract.View): TodayRatesContract.Router
{
    override fun openBaseCurrencySelectorDialog() {
        val dialog = CurrencySelectorDialogFragment.newInstance()
        dialog.show(view.fragmentManager(), CURRENCY_SELECTOR_DIALOG_TAG)
    }

    override fun startRatesHistoryScreen(targetCurrency: String) {
        view.viewContext().apply {
            startActivity(ExchangeRateHistoryActivity.newIntent(this, targetCurrency))
        }
    }

    companion object {
        const val CURRENCY_SELECTOR_DIALOG_TAG = "fragment_currency_selector_dialog"
    }
}