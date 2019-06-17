package hu.tamasgomory.exchangerates.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import hu.tamasgomory.exchangerates.ui.todayrates.TodayRatesActivity
import hu.tamasgomory.exchangerates.ui.todayrates.TodayRatesModule

@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector(modules = [(TodayRatesModule::class)])
    abstract fun bindsTodayRatesActivity(): TodayRatesActivity
}