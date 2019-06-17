package hu.tamasgomory.exchangerates.service

import android.app.Application
import android.content.Context
import android.telephony.TelephonyManager
import java.util.*
import javax.inject.Inject

class CurrencyService
@Inject
constructor(val context: Application)
{
    val currencyCodeBasedOnNetwork = {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val countryCode = tm.networkCountryIso
        Currency.getInstance(Locale("", countryCode)).currencyCode
    }
}