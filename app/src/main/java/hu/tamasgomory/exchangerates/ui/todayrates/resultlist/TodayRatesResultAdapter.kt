package hu.tamasgomory.exchangerates.ui.todayrates.resultlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import hu.tamasgomory.exchangerates.R
import hu.tamasgomory.exchangerates.base.listener.OnCurrencySelectedListener
import kotlinx.android.synthetic.main.today_rate_list_item.view.*

class TodayRatesResultAdapter(val context: Context,
                              var listener: OnCurrencySelectedListener):
        RecyclerView.Adapter<TodayRatesResultAdapter.ViewHolder>() {

    var items: List<TodayRateListItemModel> = ArrayList()
        set(newItems) {
            field = newItems
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.today_rate_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.currencyCodeTv.text = item.currency
        holder.exchangeRateTv.text = item.exchangeRate.toString()
        holder.itemContainer.setOnClickListener { listener.onCurrencySelected(item.currency) }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemContainer: ConstraintLayout = view.itemContainer
        val currencyCodeTv: TextView = view.currencyCodeTv
        val exchangeRateTv: TextView = view.exchangeRateTv
    }
}