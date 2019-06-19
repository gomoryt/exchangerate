package hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor

import dagger.Binds
import dagger.Module

@Module
abstract class CurrencySelectorModule {
    @Binds
    abstract fun bindCurrencySelectorView(view: CurrencySelectorDialogFragment): CurrencySelectorContract.View

    @Binds
    abstract fun bindCurrencySelectorPresenter(presenter: CurrencySelectorPresenter): CurrencySelectorContract.Presenter

    @Binds
    abstract fun bindCurrencySelectorInteractor(interactor: CurrencySelectorInteractor): CurrencySelectorContract.Interactor
}