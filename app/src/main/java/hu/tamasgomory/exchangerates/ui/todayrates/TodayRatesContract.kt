package hu.tamasgomory.exchangerates.ui.todayrates

import hu.tamasgomory.exchangerates.base.IInteractor
import hu.tamasgomory.exchangerates.base.IPresenter
import hu.tamasgomory.exchangerates.base.IView

interface TodayRatesContract {
    interface View : IView {
        fun showSelectedCurrency(currencyCode: String)
    }
    interface Presenter: IPresenter {
        fun showSelectedCurrency(currencyCode: String)
    }
    interface Interactor: IInteractor<Presenter> {}
}