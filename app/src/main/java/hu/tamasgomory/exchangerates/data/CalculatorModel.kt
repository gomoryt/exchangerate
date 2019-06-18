package hu.tamasgomory.exchangerates.data

import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRateCalculatorModel
    @Inject
    constructor()
{
    var selectedBaseCurrency: BehaviorSubject<String> = BehaviorSubject.create()
    var amount: BehaviorSubject<Double> = BehaviorSubject.create()
    var rates: BehaviorSubject<HashMap<String, Double>> = BehaviorSubject.create()

}