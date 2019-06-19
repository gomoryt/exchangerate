package hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor

import hu.tamasgomory.exchangerates.base.BaseInteractor
import hu.tamasgomory.exchangerates.data.ExchangeRateCalculatorModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrencySelectorInteractor
    @Inject
    constructor(private val calculatorModel: ExchangeRateCalculatorModel):
    BaseInteractor<CurrencySelectorContract.Presenter>(),
    CurrencySelectorContract.Interactor
{

    override fun init() {
        compositeDisposable.add(
                calculatorModel.rates
                        .map { it.keys.toList() }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy {
                            presenter.availableCurrenciesReceived(it)
                        }
        )
    }

    override fun changeBaseCurrency(currency: String) {
        calculatorModel.selectedBaseCurrency.onNext(currency.toUpperCase())
    }
}