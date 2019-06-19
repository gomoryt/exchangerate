package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import hu.tamasgomory.exchangerates.base.BasePresenter
import hu.tamasgomory.exchangerates.base.IRouter
import hu.tamasgomory.exchangerates.data.api.response.ExchangeRateHistoryResponse
import hu.tamasgomory.exchangerates.util.CurrencyUtil
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.pow
import kotlin.math.round

class ExchangeRateHistoryPresenter
    @Inject
    constructor(
            view: ExchangeRateHistoryContract.View,
            interactor: ExchangeRateHistoryContract.Interactor,
            val currencyUtil: CurrencyUtil
    ):
    BasePresenter<ExchangeRateHistoryContract.View, IRouter, ExchangeRateHistoryContract.Interactor>(
            view,
            null,
            interactor
    ),
    ExchangeRateHistoryContract.Presenter
{

    override fun onCreate() {
        super<BasePresenter>.onCreate()
        interactor!!.fetchExchangeRateHistory(view.targetCurrency)
    }

    override fun onExchangeRatesHistoryResponseReceived(response: ExchangeRateHistoryResponse, amount: Double) {
        val currency = Currency.getInstance(view.targetCurrency)
        val columns = ArrayList<ExchangeHistoryGraphView.ColumnModel>()
        response.rates.keys.forEach {date ->
            val rate = response.rates[date]?.get(view.targetCurrency)?.times(amount) ?: 0.0

            val roundedRate = currencyUtil.roundCurrency(currency, rate*amount)
            columns.add(ExchangeHistoryGraphView.ColumnModel(roundedRate, date))

        }
        columns.sortBy { it.date }
        view.showRates(amount, response.base, columns)
    }
}