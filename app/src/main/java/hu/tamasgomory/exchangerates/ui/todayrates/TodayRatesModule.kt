package hu.tamasgomory.exchangerates.ui.todayrates

import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class TodayRatesModule {

    @Binds
    abstract fun bindsTodayRatesPresenter(presenter: TodayRatesPresenter): TodayRatesContract.Presenter

    @Binds
    abstract fun bindsTodayRatesView(view: TodayRatesActivity): TodayRatesContract.View

    @Binds
    abstract fun bindsTodayRatesInteractor(interactor: TodayRatesInteractor): TodayRatesContract.Interactor

    @Binds
    abstract fun bindsTodayRatesRouter(router: TodayRatesRouter): TodayRatesContract.Router

}