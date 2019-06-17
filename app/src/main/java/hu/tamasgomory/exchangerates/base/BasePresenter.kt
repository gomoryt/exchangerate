package hu.tamasgomory.exchangerates.base

import hu.tamasgomory.exchangerates.ui.todayrates.TodayRatesContract
import javax.inject.Inject

abstract class BasePresenter(val interactor: IInteractor<IPresenter>) : IPresenter {
    init {
        interactor.presenter = this
    }
}