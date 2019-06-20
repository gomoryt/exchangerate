package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import com.nhaarman.mockitokotlin2.*
import hu.tamasgomory.exchangerates.data.api.response.ExchangeRateHistoryResponse
import hu.tamasgomory.exchangerates.util.CurrencyUtil
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ExchangeRateHistoryPresenterTest {

    @Mock
    lateinit var view: ExchangeRateHistoryContract.View

    @Mock
    lateinit var interactor: ExchangeRateHistoryContract.Interactor

    @Mock
    lateinit var currencyUtil: CurrencyUtil

    lateinit var subject: ExchangeRateHistoryPresenter

    val dummyCurrencyCode = "EUR"
    val dummyCurrencyCode2 = "HUF"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        subject = ExchangeRateHistoryPresenter(view, interactor, currencyUtil)
    }

    @Test
    fun onCreate() {
        whenever(view.targetCurrency).thenReturn(dummyCurrencyCode)

        subject.onCreate()

        verify(interactor).fetchExchangeRateHistory(dummyCurrencyCode)

    }

    @Test
    fun onExchangeRatesHistoryResponseReceived() {
        val amount = 2.0
        val ratesByDate = HashMap<String,HashMap<String, Double>>()

        val rates1 = HashMap<String, Double>()
        rates1.put(dummyCurrencyCode, 322.1230)
        val rates2 = HashMap<String, Double>()
        rates2.put(dummyCurrencyCode, 324.0)
        val rates3 = HashMap<String, Double>()
        rates3.put(dummyCurrencyCode, 320.546)
        val date1 = "2019-06-18"
        val date2 = "2019-06-17"
        val date3 = "2019-06-19"
        ratesByDate.put(date1, rates1)
        ratesByDate.put(date2, rates2)
        ratesByDate.put(date3, rates3)

        val response = ExchangeRateHistoryResponse(dummyCurrencyCode, ratesByDate)

        val columnsListCaptor = argumentCaptor<List<ExchangeHistoryGraphView.ColumnModel>>()
        val amountCaptor = argumentCaptor<Double>()
        val cuurrencyCaptor = argumentCaptor<String>()

        whenever(view.targetCurrency).thenReturn(dummyCurrencyCode)
        whenever(currencyUtil.roundCurrency(any(), any()))
                .thenReturn(rates1[dummyCurrencyCode]!!.times(amount))
                .thenReturn(rates2[dummyCurrencyCode]!!.times(amount))
                .thenReturn(rates3[dummyCurrencyCode]!!.times(amount))

        subject.onExchangeRatesHistoryResponseReceived(response, amount)

        verify(view).showRates(amountCaptor.capture(), cuurrencyCaptor.capture(), columnsListCaptor.capture())

        assert(amountCaptor.firstValue == amount)
        assert(cuurrencyCaptor.firstValue == dummyCurrencyCode)

        val columnsList = columnsListCaptor.firstValue

        assertEquals(columnsList[0].date, date2)
        assertEquals(columnsList[1].date, date1)
        assertEquals(columnsList[2].date, date3)

        assert(columnsList[0].rate == rates2[dummyCurrencyCode]!!.times(amount))
        assert(columnsList[1].rate == rates1[dummyCurrencyCode]!!.times(amount))
        assert(columnsList[2].rate == rates3[dummyCurrencyCode]!!.times(amount))

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