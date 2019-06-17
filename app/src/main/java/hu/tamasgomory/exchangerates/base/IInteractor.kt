package hu.tamasgomory.exchangerates.base

interface IInteractor<P: IPresenter> {
    var presenter: P
}