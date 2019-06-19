package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import hu.tamasgomory.exchangerates.base.IInteractor
import hu.tamasgomory.exchangerates.base.IPresenter
import hu.tamasgomory.exchangerates.base.IRouter
import hu.tamasgomory.exchangerates.base.IView

class ExchangeRateHistoryContract {
    interface View: IView {
        var targetCurrency: String
    }
    interface Presenter: IPresenter {

    }
    interface Interactor: IInteractor<Presenter> {
    }
    interface Router: IRouter
}