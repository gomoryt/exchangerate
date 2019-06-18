package hu.tamasgomory.exchangerates.data.api

import hu.tamasgomory.exchangerates.data.api.response.TodayRatesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApiService {

    @GET("/latest")
    fun latestExchangeRates(@Query("base") base: String?): Single<TodayRatesResponse>
}