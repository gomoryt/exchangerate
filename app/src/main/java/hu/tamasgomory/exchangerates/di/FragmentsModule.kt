package hu.tamasgomory.exchangerates.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor.CurrencySelectorDialogFragment
import hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor.CurrencySelectorModule

@Module
abstract class FragmentsModule {
    @ContributesAndroidInjector(modules = [(CurrencySelectorModule::class)])
    abstract fun bindsCurrencySelectorDialogFragment(): CurrencySelectorDialogFragment
}