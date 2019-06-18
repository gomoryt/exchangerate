package hu.tamasgomory.exchangerates.base

abstract class BasePresenter<out V: IView, out I: IInteractor<*>>(val view: V, val interactor: I) : IPresenter {

    init {
        @Suppress("UNCHECKED_CAST")
        (interactor as IInteractor<IPresenter>).presenter = this
    }


    override fun onCreate() {
        super.onCreate()
        interactor.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        interactor.destroy()
    }
}