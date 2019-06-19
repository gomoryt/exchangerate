package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import dagger.Binds
import dagger.Module

@Module
abstract class ExchangeRateHistoryModule {
    @Binds
    abstract fun bindExchangeRateHistoryView(view: ExchangeRateHistoryActivity): ExchangeRateHistoryContract.View

    @Binds
    abstract fun bindExchangeRateHistoryPresenter(presenter: ExchangeRateHistoryPresenter): ExchangeRateHistoryContract.Presenter

    @Binds
    abstract fun bindExchangeRateHistoryInteractor(interactor: ExchangeRateHistoryInteractor): ExchangeRateHistoryContract.Interactor
}