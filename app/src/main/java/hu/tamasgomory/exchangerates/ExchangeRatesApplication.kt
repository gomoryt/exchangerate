package hu.tamasgomory.exchangerates

import dagger.android.DaggerApplication
import hu.tamasgomory.exchangerates.di.DaggerAppComponent
import net.danlew.android.joda.JodaTimeAndroid

class ExchangeRatesApplication: DaggerApplication() {
    override fun applicationInjector() = DaggerAppComponent.builder()
            .application(this)
            .build()

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }
}