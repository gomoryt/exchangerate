package hu.tamasgomory.exchangerates.data.api

import hu.tamasgomory.exchangerates.data.api.response.ExchangeRateHistoryResponse
import hu.tamasgomory.exchangerates.data.api.response.TodayRatesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApiService {

    @GET("/latest")
    fun latestExchangeRates(@Query("base") base: String?): Single<TodayRatesResponse>

    @GET("/history")
    fun exchangeRatesHistory(
            @Query("base") base: String,
            @Query("start_at") startAt: String,
            @Query("end_at") endAt: String,
            @Query("symbols") targetCurrency: String
    ): Single<ExchangeRateHistoryResponse>
}