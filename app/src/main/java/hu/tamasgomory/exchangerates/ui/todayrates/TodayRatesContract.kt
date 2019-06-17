package hu.tamasgomory.exchangerates.ui.todayrates

import hu.tamasgomory.exchangerates.base.IInteractor
import hu.tamasgomory.exchangerates.base.IPresenter

interface TodayRatesContract {
    interface View {}
    interface Presenter: IPresenter {}
    interface Interactor: IInteractor<Presenter> {}
}