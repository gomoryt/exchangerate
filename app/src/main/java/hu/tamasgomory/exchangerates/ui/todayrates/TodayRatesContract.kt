package hu.tamasgomory.exchangerates.ui.todayrates

import hu.tamasgomory.exchangerates.base.IInteractor
import hu.tamasgomory.exchangerates.base.IPresenter
import hu.tamasgomory.exchangerates.base.IView

interface TodayRatesContract {
    interface View : IView {
        fun showSelectedCurrency(currencyCode: String)
        fun showAmount(amount: Double)
    }
    interface Presenter: IPresenter {
        fun showSelectedCurrency(currencyCode: String)
        fun onAmountChanged(amount: Double)
    }
    interface Interactor: IInteractor<Presenter> {
        fun onAmountChanged(amount: Double)
    }
}