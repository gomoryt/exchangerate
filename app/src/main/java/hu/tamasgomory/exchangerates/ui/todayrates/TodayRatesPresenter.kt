package hu.tamasgomory.exchangerates.ui.todayrates

import hu.tamasgomory.exchangerates.base.BasePresenter
import javax.inject.Inject
import android.util.Log
import hu.tamasgomory.exchangerates.base.IInteractor
import hu.tamasgomory.exchangerates.base.IPresenter

class TodayRatesPresenter
    @Inject
    constructor(interactor: TodayRatesContract.Interactor):
        BasePresenter(interactor as IInteractor<IPresenter>),
        TodayRatesContract.Presenter
{
    override fun onCreate() {
        super<BasePresenter>.onCreate()
        Log.d("TodayRatesPresenter", "onCreate")
    }
}