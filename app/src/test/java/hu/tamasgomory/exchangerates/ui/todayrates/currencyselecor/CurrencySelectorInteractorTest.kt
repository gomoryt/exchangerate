package hu.tamasgomory.exchangerates.ui.todayrates.currencyselecor

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import hu.tamasgomory.exchangerates.data.ExchangeRateCalculatorModel
import hu.tamasgomory.exchangerates.rule.RxImmediateSchedulerRule
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CurrencySelectorInteractorTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var presenter: CurrencySelectorContract.Presenter

    lateinit var calculatorModel: ExchangeRateCalculatorModel

    lateinit var subject: CurrencySelectorInteractor

    val dummyCurrencyCode = "EUR"
    val dummyCurrencyCode2 = "HUF"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        calculatorModel = ExchangeRateCalculatorModel()
        subject = CurrencySelectorInteractor(calculatorModel)
        subject.presenter = presenter

    }

    @Test
    fun init() {
        val rates = HashMap<String, Double>()
        rates.put(dummyCurrencyCode, 1.0)
        rates.put(dummyCurrencyCode2, 1.0)

        calculatorModel.rates.onNext(rates)

        subject.init()


        val currencyListCaptor = argumentCaptor<List<String>>()
        verify(presenter).availableCurrenciesReceived(currencyListCaptor.capture())

        val currencyList = currencyListCaptor.firstValue
        assertEquals(2, currencyList.size)
        assert(currencyList.contains(dummyCurrencyCode))
        assert(currencyList.contains(dummyCurrencyCode2))
    }

    @Test
    fun changeBaseCurrency() {
        assert(!calculatorModel.selectedBaseCurrency.hasValue())

        subject.changeBaseCurrency(dummyCurrencyCode)

        assertEquals(calculatorModel.selectedBaseCurrency.value, dummyCurrencyCode)
    }
}