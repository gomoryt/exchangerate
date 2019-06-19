package hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import hu.tamasgomory.exchangerates.R
import hu.tamasgomory.exchangerates.base.BaseDialogFragment


class CurrencySelectorDialogFragment: BaseDialogFragment<CurrencySelectorContract.Presenter>(), CurrencySelectorContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_currency_selector_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.setTitle("Asd")
    }

    companion object {
        fun newInstance(): CurrencySelectorDialogFragment {
            return CurrencySelectorDialogFragment()
        }
    }
}