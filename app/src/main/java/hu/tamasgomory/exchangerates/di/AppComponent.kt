package hu.tamasgomory.exchangerates.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import hu.tamasgomory.exchangerates.ExchangeRatesApplication
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            ActivitiesModule::class,
            FragmentsModule::class,
            ApiModule::class
        ]
)
interface AppComponent: AndroidInjector<ExchangeRatesApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(application: ExchangeRatesApplication)
}