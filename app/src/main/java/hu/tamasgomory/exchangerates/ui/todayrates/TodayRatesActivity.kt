package hu.tamasgomory.exchangerates.ui.todayrates

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import hu.tamasgomory.exchangerates.R
import hu.tamasgomory.exchangerates.base.BaseActivity
import hu.tamasgomory.exchangerates.ui.todayrates.resultlist.TodayRateListItemModel
import hu.tamasgomory.exchangerates.ui.todayrates.resultlist.TodayRatesResultAdapter
import hu.tamasgomory.exchangerates.util.ViewUtils
import kotlinx.android.synthetic.main.activity_today_rates.*

class TodayRatesActivity : BaseActivity<TodayRatesContract.Presenter>(), TodayRatesContract.View {

    private lateinit var adapter: TodayRatesResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_rates)

        adapter = TodayRatesResultAdapter(this, presenter)
        ratesResultListView.adapter = adapter
        ratesResultListView.layoutManager = LinearLayoutManager(this)

        amountEt.addTextChangedListener( object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onAmountChanged(p0.toString().toDouble())
            }
        })

        selectedCurrencyTv.setOnClickListener { presenter.onCurrencyCodeClicked() }
    }

    override fun showSelectedCurrency(currencyCode: String) {
        selectedCurrencyTv.text = currencyCode
    }

    override fun showAmount(amount: Double) {
        amountEt.text = Editable.Factory.getInstance().newEditable(amount.toString())
    }

    override fun showExchangeRateResult(rates: List<TodayRateListItemModel>) {
        adapter.items = rates
        switchLayout(resultVisibility = true)
    }

    override fun showError() {
        switchLayout(errorVisibility = true)
    }

    override fun showLoading() {
        switchLayout(loadingVisibility = true)
    }

    private fun switchLayout(loadingVisibility: Boolean = false, errorVisibility: Boolean = false, resultVisibility: Boolean = false) {
        ViewUtils.toggle(progressBar, loadingVisibility)
        ViewUtils.toggle(resultView, resultVisibility)
        ViewUtils.toggle(errorView, errorVisibility)
    }
}
