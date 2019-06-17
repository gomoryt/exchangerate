package hu.tamasgomory.exchangerates.ui.todayrates

import android.util.Log
import javax.inject.Inject
import hu.tamasgomory.exchangerates.base.BaseInteractor
import java.util.*

class TodayRatesInteractor
    @Inject constructor():
        BaseInteractor<TodayRatesContract.Presenter>(), TodayRatesContract.Interactor {

    override fun init() {
        
    }
}