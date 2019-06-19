package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import android.util.Log
import hu.tamasgomory.exchangerates.base.BasePresenter
import hu.tamasgomory.exchangerates.base.IRouter
import javax.inject.Inject

class ExchangeRateHistoryPresenter
    @Inject
    constructor(
            view: ExchangeRateHistoryContract.View,
            interactor: ExchangeRateHistoryContract.Interactor
    ):
    BasePresenter<ExchangeRateHistoryContract.View, IRouter, ExchangeRateHistoryContract.Interactor>(
            view,
            null,
            interactor
    ),
    ExchangeRateHistoryContract.Presenter
{

    override fun onCreate() {
        super<BasePresenter>.onCreate()
        Log.d("History", "TargetCurrency: " + view.targetCurrency)
    }
}