package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import hu.tamasgomory.exchangerates.data.ExchangeRateCalculatorModel
import hu.tamasgomory.exchangerates.data.api.ExchangeRatesApiService
import hu.tamasgomory.exchangerates.data.api.response.ExchangeRateHistoryResponse
import hu.tamasgomory.exchangerates.rule.RxImmediateSchedulerRule
import io.reactivex.Single
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException

class ExchangeRateHistoryInteractorTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var presenter: ExchangeRateHistoryContract.Presenter

    @Mock
    lateinit var apiService: ExchangeRatesApiService

    lateinit var calculatorModel: ExchangeRateCalculatorModel

    lateinit var subject: ExchangeRateHistoryInteractor

    val baseCurrencyCode = "EUR"
    val targetCurrencyCode = "HUF"
    val amount = 1.0

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        calculatorModel = ExchangeRateCalculatorModel()

        subject = ExchangeRateHistoryInteractor(calculatorModel, apiService)
        subject.presenter = presenter
    }

    @Test
    fun fetchExchangeRateHistory_success() {
        val response = mock(ExchangeRateHistoryResponse::class.java)
        val today = DateTime.now()
        val oneWeekAgo = today.minusWeeks(1)

        calculatorModel.selectedBaseCurrency.onNext(baseCurrencyCode)
        calculatorModel.amount.onNext(amount)

        whenever(apiService.exchangeRatesHistory(baseCurrencyCode, oneWeekAgo.toString("yyyy-MM-dd"), today.toString("yyyy-MM-dd"), targetCurrencyCode))
                .thenReturn(Single.just(response))

        subject.fetchExchangeRateHistory(targetCurrencyCode)

        verify(presenter).showLoading()
        verify(presenter).onExchangeRatesHistoryResponseReceived(response, amount)
    }


    @Test
    fun fetchExchangeRateHistory_error() {
        val error = mock(HttpException::class.java)

        calculatorModel.selectedBaseCurrency.onNext(baseCurrencyCode)

        whenever(apiService.exchangeRatesHistory(any(), any(), any(), any()))
                .thenReturn(Single.error(error))

        subject.fetchExchangeRateHistory(targetCurrencyCode)

        verify(presenter).showLoading()
        verify(presenter).showError()
    }
}