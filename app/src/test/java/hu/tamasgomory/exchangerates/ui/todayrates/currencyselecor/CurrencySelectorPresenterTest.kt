package hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor

import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CurrencySelectorPresenterTest {

    @Mock
    lateinit var view: CurrencySelectorContract.View

    @Mock
    lateinit var interactor: CurrencySelectorContract.Interactor

    lateinit var subject: CurrencySelectorPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        subject = CurrencySelectorPresenter(view, interactor)
    }

    @Test
    fun availableCurrenciesReceived() {
        val currencies = ArrayList<String>()

        subject.availableCurrenciesReceived(currencies)

        verify(view).showCurrencies(currencies)
    }

    @Test
    fun onCurrencySelected() {
        val currency = "EUR"

        subject.onCurrencySelected(currency)

        verify(interactor).changeBaseCurrency(currency)
        verify(view).dismiss()
    }
}