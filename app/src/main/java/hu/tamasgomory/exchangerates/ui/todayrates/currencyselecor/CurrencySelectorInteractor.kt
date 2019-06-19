package hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor

import hu.tamasgomory.exchangerates.base.BaseInteractor
import hu.tamasgomory.exchangerates.data.ExchangeRateCalculatorModel
import javax.inject.Inject

class CurrencySelectorInteractor
    @Inject
    constructor(val calculatorModel: ExchangeRateCalculatorModel):
    BaseInteractor<CurrencySelectorContract.Presenter>(),
    CurrencySelectorContract.Interactor
{
    override fun init() {

    }
}