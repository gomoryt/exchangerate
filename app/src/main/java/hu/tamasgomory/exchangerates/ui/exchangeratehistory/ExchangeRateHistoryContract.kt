package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import hu.tamasgomory.exchangerates.base.IInteractor
import hu.tamasgomory.exchangerates.base.IPresenter
import hu.tamasgomory.exchangerates.base.IRouter
import hu.tamasgomory.exchangerates.base.IView
import hu.tamasgomory.exchangerates.data.api.response.ExchangeRateHistoryResponse

class ExchangeRateHistoryContract {
    interface View: IView {
        var targetCurrency: String
        fun showRates(amount: Double, baseCurrency: String, rates: List<ExchangeHistoryGraphView.ColumnModel>)
    }
    interface Presenter: IPresenter {

        fun onExchangeRatesHistoryResponseReceived(response: ExchangeRateHistoryResponse, amount: Double)
    }
    interface Interactor: IInteractor<Presenter> {
        fun fetchExchangeRateHistory(targetCurrency: String)
    }
    interface Router: IRouter
}