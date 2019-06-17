package hu.tamasgomory.exchangerates.ui.todayrates

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TodayRatesModule {
    @ContributesAndroidInjector
    abstract fun contributeTodayRatesActivity(): TodayRatesActivity

    @Binds
    abstract fun bindsTodayRatesPresenter(presenter: TodayRatesPresenter): TodayRatesContract.Presenter
    @Binds
    abstract fun bindsTodayRatesInteractor(interactor: TodayRatesInteractor): TodayRatesContract.Interactor
}