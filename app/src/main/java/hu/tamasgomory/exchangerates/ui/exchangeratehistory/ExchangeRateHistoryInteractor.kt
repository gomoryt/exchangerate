package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import hu.tamasgomory.exchangerates.base.BaseInteractor
import javax.inject.Inject

class ExchangeRateHistoryInteractor
    @Inject
    constructor():
    BaseInteractor<ExchangeRateHistoryContract.Presenter>(),
    ExchangeRateHistoryContract.Interactor
{

}