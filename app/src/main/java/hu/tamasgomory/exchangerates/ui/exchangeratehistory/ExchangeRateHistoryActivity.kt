package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import hu.tamasgomory.exchangerates.R
import hu.tamasgomory.exchangerates.base.BaseActivity

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

    companion object {
        const val EXTRA_TARGET_CURRENCY = "ExchangeRateHistoryActivity.EXTRA_TARGET_CURRENCY"

        fun newIntent(context: Context, targetCurrency: String): Intent {
            return Intent(context, ExchangeRateHistoryActivity::class.java).apply {
                putExtra(EXTRA_TARGET_CURRENCY, targetCurrency)
            }
        }
    }

}