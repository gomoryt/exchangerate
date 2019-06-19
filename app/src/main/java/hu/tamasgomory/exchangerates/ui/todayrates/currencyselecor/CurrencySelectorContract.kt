package hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor

import hu.tamasgomory.exchangerates.base.IInteractor
import hu.tamasgomory.exchangerates.base.IPresenter
import hu.tamasgomory.exchangerates.base.IRouter
import hu.tamasgomory.exchangerates.base.IView

interface CurrencySelectorContract {
    interface View: IView {}
    interface Presenter: IPresenter {

    }
    interface Interactor: IInteractor<Presenter>
    interface Router: IRouter
}