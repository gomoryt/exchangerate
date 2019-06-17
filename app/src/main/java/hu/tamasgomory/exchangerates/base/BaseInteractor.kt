package hu.tamasgomory.exchangerates.base

abstract class BaseInteractor<P: IPresenter> : IInteractor<P> {
    override lateinit var presenter: P
}