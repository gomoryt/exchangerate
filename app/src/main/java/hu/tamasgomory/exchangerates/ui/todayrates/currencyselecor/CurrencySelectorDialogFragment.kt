package hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import hu.tamasgomory.exchangerates.R
import hu.tamasgomory.exchangerates.base.BaseDialogFragment
import hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor.currencylist.CurrencyListAdapter
import kotlinx.android.synthetic.main.fragment_currency_selector_dialog.*
import kotlinx.android.synthetic.main.fragment_currency_selector_dialog.view.*


class CurrencySelectorDialogFragment: BaseDialogFragment<CurrencySelectorContract.Presenter>(), CurrencySelectorContract.View {

    private var adapter: CurrencyListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_currency_selector_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencyList.layoutManager = LinearLayoutManager(this.viewContext())

        adapter = CurrencyListAdapter(this.viewContext(), presenter)
        currencyList.adapter = adapter
    }

    override fun showCurrencies(currencies: List<String>) {
        adapter?.items = currencies
    }

    companion object {
        fun newInstance(): CurrencySelectorDialogFragment {
            return CurrencySelectorDialogFragment()
        }
    }
}