package hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor.currencylist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.tamasgomory.exchangerates.R
import hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor.CurrencySelectorContract
import kotlinx.android.synthetic.main.currency_list_item.view.*

class CurrencyListAdapter(
        val context: Context,
        var listener: CurrencySelectorContract.OnCurrencySelectedListener
    ): RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {

    var items: List<String> = ArrayList()
        set(newItems) {
            field = newItems
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.currency_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.currencyCodeTv.text = item
        holder.currencyCodeTv.setOnClickListener { listener.onCurrencySelected(item) }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val currencyCodeTv = view.currencyCodeTv
    }

}