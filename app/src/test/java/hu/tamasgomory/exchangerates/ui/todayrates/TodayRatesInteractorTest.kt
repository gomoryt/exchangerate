package hu.tamasgomory.exchangerates.ui.todayrates

import android.util.Log
import com.nhaarman.mockitokotlin2.*
import hu.tamasgomory.exchangerates.data.ExchangeRateCalculatorModel
import hu.tamasgomory.exchangerates.data.api.ExchangeRatesApiService
import hu.tamasgomory.exchangerates.data.api.response.TodayRatesResponse
import hu.tamasgomory.exchangerates.rule.RxImmediateSchedulerRule
import hu.tamasgomory.exchangerates.util.CurrencyUtil
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import java.util.HashMap

class TodayRatesInteractorTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var currencyUtil: CurrencyUtil

    @Mock
    lateinit var apiService: ExchangeRatesApiService

    @Mock
    lateinit var presenter: TodayRatesContract.Presenter


    lateinit var calculatorModel: ExchangeRateCalculatorModel

    lateinit var subject: TodayRatesInteractor

    val dummyCurrencyCode = "EUR"
    val dummyCurrencyCode2 = "HUF"
    val dummyAmount = 322.12

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        calculatorModel = ExchangeRateCalculatorModel()

        subject = spy(TodayRatesInteractor(currencyUtil, calculatorModel, apiService))
        subject.presenter = presenter
    }

    @Test
    fun init() {
        val baseCurrencyDisposable = mock(Disposable::class.java)
        val resultDisposable = mock(Disposable::class.java)

        doReturn(baseCurrencyDisposable).whenever(subject).subscribeToBaseCurrencyChange()
        doReturn(resultDisposable).whenever(subject).subscribeToCalculatedRatesResult()
        doNothing().whenever(subject).chooseBaseCurrency()

        subject.init()

        verify(subject).subscribeToCalculatedRatesResult()
        verify(subject).subscribeToBaseCurrencyChange()
        verify(subject).chooseBaseCurrency()
        assertEquals(2, subject.compositeDisposable.size())

    }

    @Test
    fun chooseBaseCurrency_notSelectedYet() {

        whenever(currencyUtil.currencyCodeBasedOnNetwork).thenReturn { dummyCurrencyCode }

        subject.chooseBaseCurrency()

        assertEquals(calculatorModel.selectedBaseCurrency.value, dummyCurrencyCode)

    }


    @Test
    fun chooseBaseCurrency_alreadySelected() {
        calculatorModel.selectedBaseCurrency.onNext(dummyCurrencyCode2)

        whenever(currencyUtil.currencyCodeBasedOnNetwork).thenReturn { dummyCurrencyCode }

        subject.chooseBaseCurrency()

        assertEquals(calculatorModel.selectedBaseCurrency.value, dummyCurrencyCode2)

    }

    @Test
    fun subscribeToBaseCurrencyChange_baseCurrencyNotSelectedYet() {

        subject.subscribeToBaseCurrencyChange()

        verify(subject, never()).fetchLatestExchangeRates(any())
    }


    @Test
    fun subscribeToBaseCurrencyChange_baseCurrencySelectedAlready() {
        calculatorModel.selectedBaseCurrency.onNext(dummyCurrencyCode)

        doNothing().whenever(subject).fetchLatestExchangeRates(any())

        subject.subscribeToBaseCurrencyChange()

        verify(subject).fetchLatestExchangeRates(dummyCurrencyCode)
    }

    @Test
    fun subscribeToCalculatedRatesResult() {
        val rates = HashMap<String, Double>()
        rates[dummyCurrencyCode] = 1.0
        rates[dummyCurrencyCode2] = 322.0032115

        doNothing().whenever(presenter).exchangeRatesResultReceived(any(), any(), any())

        subject.subscribeToCalculatedRatesResult()

        calculatorModel.selectedBaseCurrency.onNext(dummyCurrencyCode)

        verify(presenter, never()).exchangeRatesResultReceived(any(), any(), any())

        calculatorModel.amount.onNext(dummyAmount)
        verify(presenter, never()).exchangeRatesResultReceived(any(), any(), any())

        calculatorModel.rates.onNext(rates)
        verify(presenter).exchangeRatesResultReceived(dummyCurrencyCode, dummyAmount, rates)


    }

    @Test
    fun onAmountChanged() {
        assert(!calculatorModel.amount.hasValue())

        subject.onAmountChanged(dummyAmount)

        assertEquals(calculatorModel.amount.value, dummyAmount)
    }

    @Test
    fun fetchLatestExchangeRates_success() {
        val rates = HashMap<String, Double>()
        rates[dummyCurrencyCode] = 1.0
        rates[dummyCurrencyCode2] = 322.0032115
        val mockResponse = mock(TodayRatesResponse::class.java)

        whenever(mockResponse.rates).thenReturn(rates)
        whenever(mockResponse.base).thenReturn(dummyCurrencyCode)
        whenever(apiService.latestExchangeRates(dummyCurrencyCode)).thenReturn(Single.just(mockResponse))
        calculatorModel.selectedBaseCurrency.onNext(dummyCurrencyCode)

        subject.fetchLatestExchangeRates(dummyCurrencyCode)

        verify(presenter).showLoading()
        assertEquals(calculatorModel.rates.value, rates)
        assertEquals(calculatorModel.selectedBaseCurrency.value, dummyCurrencyCode)

    }


    @Test
    fun fetchLatestExchangeRates_badRequest() {
        val rates = HashMap<String, Double>()
        rates[dummyCurrencyCode] = 1.0
        rates[dummyCurrencyCode2] = 322.0032115
        val mockResponse = mock(TodayRatesResponse::class.java)
        val mockError = mock(HttpException::class.java)

        whenever(mockError.code()).thenReturn(400)
        whenever(apiService.latestExchangeRates(dummyCurrencyCode2)).thenReturn(Single.error(mockError))

        whenever(mockResponse.rates).thenReturn(rates)
        whenever(mockResponse.base).thenReturn(dummyCurrencyCode)
        whenever(apiService.latestExchangeRates("")).thenReturn(Single.just(mockResponse))

        calculatorModel.selectedBaseCurrency.onNext(dummyCurrencyCode2)

        subject.fetchLatestExchangeRates(dummyCurrencyCode2)

        verify(presenter, times(2)).showLoading()
        verify(presenter, never()).showError()
        verify(subject).fetchLatestExchangeRates("")

        assertEquals(calculatorModel.rates.value, rates)
        assertEquals(calculatorModel.selectedBaseCurrency.value, dummyCurrencyCode)

    }



    @Test
    fun fetchLatestExchangeRates_error() {
        val mockError = mock(HttpException::class.java)

        whenever(mockError.code()).thenReturn(500)
        whenever(apiService.latestExchangeRates(dummyCurrencyCode2)).thenReturn(Single.error(mockError))

        subject.fetchLatestExchangeRates(dummyCurrencyCode2)

        verify(presenter).showLoading()
        verify(presenter).showError()

        assert(!calculatorModel.rates.hasValue())

    }
}