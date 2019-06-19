package hu.tamasgomory.exchangerates.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

abstract class BaseDialogFragment<P: IPresenter>: DaggerDialogFragment(), IView {

    @Inject
    lateinit var presenter: P

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(presenter)
        return view
    }

    override fun viewContext(): Context {
        return context!!
    }

    override fun fragmentManager(): FragmentManager {
        return fragmentManager!!
    }
}