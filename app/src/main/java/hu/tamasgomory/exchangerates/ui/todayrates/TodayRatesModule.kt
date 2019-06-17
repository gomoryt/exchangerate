package hu.tamasgomory.exchangerates.ui.todayrates

import dagger.Binds
import dagger.Module

@Module
abstract class TodayRatesModule {

    @Binds
    abstract fun bindsTodayRatesPresenter(presenter: TodayRatesPresenter): TodayRatesContract.Presenter

    @Binds
    abstract fun bindsTodayRatesView(view: TodayRatesActivity): TodayRatesContract.View

    @Binds
    abstract fun bindsTodayRatesInteractor(interactor: TodayRatesInteractor): TodayRatesContract.Interactor

}