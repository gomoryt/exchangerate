package hu.tamasgomory.exchangerates.base

import dagger.android.DaggerActivity
import javax.inject.Inject

abstract class BaseActivity<P: IPresenter>: DaggerActivity() {

    @Inject
    lateinit var presenter: P
}