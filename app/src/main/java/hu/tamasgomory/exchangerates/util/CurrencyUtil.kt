package hu.tamasgomory.exchangerates.util

import android.app.Application
import android.content.Context
import android.telephony.TelephonyManager
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.pow
import kotlin.math.round

@Singleton
class CurrencyUtil
@Inject
constructor(val context: Application)
{
    val currencyCodeBasedOnNetwork = {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val countryCode = tm.networkCountryIso
        Currency.getInstance(Locale("", countryCode)).currencyCode
    }

    fun roundCurrency(currency: Currency, amount: Double): Double {
        val roundMultiplier = 10f.pow(currency.defaultFractionDigits)
        return round(amount*roundMultiplier) / roundMultiplier
    }
}