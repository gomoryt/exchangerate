package hu.tamasgomory.exchangerates

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasAndroidInjector
import hu.tamasgomory.exchangerates.di.DaggerAppComponent

class ExchangeRatesApplication: DaggerApplication() {
    override fun applicationInjector() = DaggerAppComponent.builder()
            .application(this)
            .build()
}