package hu.tamasgomory.exchangerates.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<P: IPresenter>: DaggerAppCompatActivity(), IView {

    @Inject
    lateinit var presenter: P


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(presenter)
    }

    override fun viewContext(): Context {
        return this
    }
}