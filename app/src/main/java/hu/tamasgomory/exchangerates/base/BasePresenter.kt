package hu.tamasgomory.exchangerates.base

abstract class BasePresenter(val interactor: IInteractor<IPresenter>) : IPresenter {
    init {
        interactor.presenter = this
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