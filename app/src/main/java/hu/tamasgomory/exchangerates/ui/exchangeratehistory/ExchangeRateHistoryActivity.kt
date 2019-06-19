package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
        graphView.headerModel = ExchangeHistoryGraphView.HeaderModel(1.0, "EUR", "HUF")
        graphView.columns = listOf(
                ExchangeHistoryGraphView.ColumnModel(300.12, "2019-06-12"),
                ExchangeHistoryGraphView.ColumnModel(350.12, "2019-06-13"),
                ExchangeHistoryGraphView.ColumnModel(330.12, "2019-06-14"),
                ExchangeHistoryGraphView.ColumnModel(390.12, "2019-06-15"),
                ExchangeHistoryGraphView.ColumnModel(352.12, "2019-06-16"),
                ExchangeHistoryGraphView.ColumnModel(351.12, "2019-06-17"),
                ExchangeHistoryGraphView.ColumnModel(312.12, "2019-06-18")
        )
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