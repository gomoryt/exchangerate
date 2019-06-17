package hu.tamasgomory.exchangerates.ui.todayrates

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.android.DaggerActivity
import hu.tamasgomory.exchangerates.R
import hu.tamasgomory.exchangerates.base.BaseActivity

class TodayRatesActivity : BaseActivity<TodayRatesContract.Presenter>(), TodayRatesContract.View {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_rates)
    }
}
