package hu.tamasgomory.exchangerates.ui.todayrates.resultlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.tamasgomory.exchangerates.R
import kotlinx.android.synthetic.main.today_rate_list_item.view.*

class TodayRatesResultAdapter(val context: Context): RecyclerView.Adapter<TodayRatesResultAdapter.ViewHolder>() {

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
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val currencyCodeTv = view.currencyCodeTv
        val exchangeRateTv = view.exchangeRateTv
    }
}