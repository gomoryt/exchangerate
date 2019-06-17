package hu.tamasgomory.exchangerates.ui.todayrates

import javax.inject.Inject
import hu.tamasgomory.exchangerates.base.BaseInteractor
import hu.tamasgomory.exchangerates.service.CurrencyService

class TodayRatesInteractor
    @Inject constructor(val currencyService: CurrencyService):
        BaseInteractor<TodayRatesContract.Presenter>(), TodayRatesContract.Interactor {

    override fun init() {
        presenter.showSelectedCurrency(currencyService.currencyCodeBasedOnNetwork())
    }
}