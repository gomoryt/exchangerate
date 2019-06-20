package hu.tamasgomory.exchangerates.ui.todayrates

import com.nhaarman.mockitokotlin2.*
import hu.tamasgomory.exchangerates.ui.todayrates.resultlist.TodayRateListItemModel
import hu.tamasgomory.exchangerates.util.CurrencyUtil
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class TodayRatesPresenterTest {

    @Mock
    lateinit var view: TodayRatesContract.View

    @Mock
    lateinit var router: TodayRatesContract.Router

    @Mock
    lateinit var interactor: TodayRatesContract.Interactor

    @Mock
    lateinit var currencyUtil: CurrencyUtil

    lateinit var subject: TodayRatesPresenter

    val dummyCurrencyCode = "EUR"
    val dummyCurrencyCode2 = "HUF"
    val dummyAmount = 121.0
    val dummyAmount2 = 251.021

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        subject = TodayRatesPresenter(view, router, interactor, currencyUtil)
    }

    @Test
    fun onCreate() {
        subject.onCreate()
        verify(view).showAmount(1.0)
    }

    @Test
    fun onBasedCurrencyChanged() {
        subject.onBasedCurrencyChanged(dummyCurrencyCode)

        verify(view).showSelectedCurrency(dummyCurrencyCode)

        subject.onBasedCurrencyChanged(dummyCurrencyCode2)

        verify(view).showSelectedCurrency(dummyCurrencyCode2)
    }

    @Test
    fun onAmountChanged() {
        subject.onAmountChanged(dummyAmount)

        verify(interactor).onAmountChanged(dummyAmount)

        subject.onAmountChanged(dummyAmount2)

        verify(interactor).onAmountChanged(dummyAmount2)
    }

    @Test
    fun exchangeRatesResultReceived() {
        val rates = HashMap<String, Double>()
        rates[dummyCurrencyCode] = 1.0
        rates[dummyCurrencyCode2] = 322.0032115
        val currency = Currency.getInstance(dummyCurrencyCode2)

        whenever(currencyUtil.roundCurrency(any(), any())).thenReturn(rates[dummyCurrencyCode2]!!*dummyAmount)

        subject.exchangeRatesResultReceived(dummyCurrencyCode, dummyAmount, rates)

        val ratesResultCaptor = argumentCaptor<List<TodayRateListItemModel>>()

        verify(view).showSelectedCurrency(dummyCurrencyCode)
        verify(view).showExchangeRateResult(ratesResultCaptor.capture())

        val result = ratesResultCaptor.firstValue
        val expectedRate = "%s %s". format(dummyAmount*rates[dummyCurrencyCode2]!!, currency.symbol)

        assertEquals(1, result.size)
        assertEquals(expectedRate, result[0].exchangeRate )
    }

    @Test
    fun onCurrencyCodeClicked() {
        subject.onCurrencyCodeClicked()
        verify(router).openBaseCurrencySelectorDialog()
    }

    @Test
    fun onCurrencySelected() {
        subject.onCurrencySelected(dummyCurrencyCode2)
        verify(router).startRatesHistoryScreen(dummyCurrencyCode2)
    }

    @Test
    fun showError() {
        subject.showError()
        verify(view).showError()
    }

    @Test
    fun showLoading() {
        subject.showLoading()
        verify(view).showLoading()
    }

}