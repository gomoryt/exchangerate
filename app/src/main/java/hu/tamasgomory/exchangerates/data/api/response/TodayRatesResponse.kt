package hu.tamasgomory.exchangerates.data.api.response

data class TodayRatesResponse(val base: String, val rates: HashMap<String, Double>)