package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import hu.tamasgomory.exchangerates.R
import hu.tamasgomory.exchangerates.base.BaseActivity
import kotlinx.android.synthetic.main.activity_exhange_rate_history.*
import kotlinx.android.synthetic.main.activity_exhange_rate_history.view.*

class ExchangeRateHistoryActivity:
        BaseActivity<ExchangeRateHistoryContract.Presenter>(),
        ExchangeRateHistoryContract.View
{
    override lateinit var targetCurrency: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhange_rate_history)
        targetCurrency = intent.getStringExtra(EXTRA_TARGET_CURRENCY)
    }

    override fun showRates(amount: Double, baseCurrency: String, rates: List<ExchangeHistoryGraphView.ColumnModel>) {
        graphView.headerModel = ExchangeHistoryGraphView.HeaderModel(amount, baseCurrency, targetCurrency)
        graphView.columns = rates
        graphView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    companion object {
        const val EXTRA_TARGET_CURRENCY = "ExchangeRateHistoryActivity.EXTRA_TARGET_CURRENCY"

        fun newIntent(context: Context, targetCurrency: String): Intent {
            return Intent(context, ExchangeRateHistoryActivity::class.java).apply {
                putExtra(EXTRA_TARGET_CURRENCY, targetCurrency)
            }
        }
    }

}