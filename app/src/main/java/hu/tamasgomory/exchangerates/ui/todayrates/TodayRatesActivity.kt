package hu.tamasgomory.exchangerates.ui.todayrates

import android.os.Bundle
import hu.tamasgomory.exchangerates.R
import hu.tamasgomory.exchangerates.base.BaseActivity
import kotlinx.android.synthetic.main.activity_today_rates.*

class TodayRatesActivity : BaseActivity<TodayRatesContract.Presenter>(), TodayRatesContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_rates)
    }

    override fun showSelectedCurrency(currencyCode: String) {
        selectedCurrencyTv.text = currencyCode
    }
}
