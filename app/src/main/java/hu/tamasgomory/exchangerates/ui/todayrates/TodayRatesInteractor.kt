package hu.tamasgomory.exchangerates.ui.todayrates

import javax.inject.Inject
import hu.tamasgomory.exchangerates.base.BaseInteractor
import hu.tamasgomory.exchangerates.base.IInteractor
import hu.tamasgomory.exchangerates.base.IPresenter

class TodayRatesInteractor
    @Inject constructor():
        BaseInteractor<TodayRatesContract.Presenter>(), TodayRatesContract.Interactor {

    fun init() {

    }
}