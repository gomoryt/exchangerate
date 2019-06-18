package hu.tamasgomory.exchangerates.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Provides
import javax.inject.Singleton
import android.app.Application
import dagger.Module
import okhttp3.Cache
import java.io.File
import hu.tamasgomory.exchangerates.data.api.ExchangeRatesApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named


@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder =  GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    @Singleton
    @Named("exchange-rate-api")
    internal fun provideExchangeRateApiRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.exchangeratesapi.io")
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideExchangeRatesApiService(@Named("exchange-rate-api") retrofit: Retrofit): ExchangeRatesApiService {
        return retrofit.create(ExchangeRatesApiService::class.java)
    }
}