package hu.tamasgomory.exchangerates.base

import io.reactivex.disposables.CompositeDisposable

abstract class BaseInteractor<P: IPresenter> : IInteractor<P> {
    override lateinit var presenter: P
    val compositeDisposable = CompositeDisposable()

    override fun destroy() {
        if (compositeDisposable.size() > 0) {
            compositeDisposable.dispose()
        }
    }
}