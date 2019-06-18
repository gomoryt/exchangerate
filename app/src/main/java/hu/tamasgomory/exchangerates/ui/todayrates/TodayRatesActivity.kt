package hu.tamasgomory.exchangerates.ui.todayrates

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import hu.tamasgomory.exchangerates.R
import hu.tamasgomory.exchangerates.base.BaseActivity
import kotlinx.android.synthetic.main.activity_today_rates.*

class TodayRatesActivity : BaseActivity<TodayRatesContract.Presenter>(), TodayRatesContract.View {

    private var adapter = TodayRatesResultAdapter(this)
    private var currencyAdapter : ArrayAdapter<String>? = null

    override var availableCurrencies: MutableList<String> = ArrayList()
        set(currencies) {
            if (field.isEmpty()) {
                field.addAll(currencies)
                currencyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, field)
                        .also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        }
                currencySpinner.adapter = currencyAdapter
            }
        }

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

        currencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                if (parent != null) {
                    presenter.onCurrencyChanged(parent.getItemAtPosition(pos).toString())
                }
            }
        }
    }

    override fun showSelectedCurrency(currencyCode: String) {
        currencySpinner.setSelection(availableCurrencies.indexOf(currencyCode))
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
