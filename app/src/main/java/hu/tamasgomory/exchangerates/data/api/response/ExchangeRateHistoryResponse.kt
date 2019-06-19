package hu.tamasgomory.exchangerates.data.api.response

class ExchangeRateHistoryResponse(val base: String, val rates: HashMap<String, HashMap<String, Double>>) {
}