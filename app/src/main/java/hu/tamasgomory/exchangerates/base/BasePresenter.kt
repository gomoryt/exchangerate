package hu.tamasgomory.exchangerates.base

abstract class BasePresenter<out V: IView, out R: IRouter, out I: IInteractor<*>>(val view: V, val router: R?, val interactor: I?) : IPresenter {

    init {
        if (interactor != null) {
            @Suppress("UNCHECKED_CAST")
            (interactor as IInteractor<IPresenter>).presenter = this
        }
    }


    override fun onCreate() {
        super.onCreate()
        interactor?.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        interactor?.destroy()
    }
}