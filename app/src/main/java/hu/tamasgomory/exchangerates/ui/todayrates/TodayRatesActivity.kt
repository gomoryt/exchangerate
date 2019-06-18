package hu.tamasgomory.exchangerates.ui.todayrates

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import hu.tamasgomory.exchangerates.R
import hu.tamasgomory.exchangerates.base.BaseActivity
import kotlinx.android.synthetic.main.activity_today_rates.*

class TodayRatesActivity : BaseActivity<TodayRatesContract.Presenter>(), TodayRatesContract.View {

    private var adapter = TodayRatesResultAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_rates)

        ratesResultListView.adapter = adapter
        ratesResultListView.layoutManager = LinearLayoutManager(this)

        amountEt.addTextChangedListener( object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onAmountChanged(p0.toString().toDouble())
            }
        })
    }

    override fun showSelectedCurrency(currencyCode: String) {
        selectedCurrencyTv.text = currencyCode
    }

    override fun showAmount(amount: Double) {
        amountEt.text = Editable.Factory.getInstance().newEditable(amount.toString())
    }

    override fun displayError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showExchangeRateResult(rates: List<TodayRateListItemModel>) {
        adapter.items = rates
    }
}
