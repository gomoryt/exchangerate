package hu.tamasgomory.exchangerates.base

import android.content.Context
import androidx.fragment.app.FragmentManager

interface IView {
    fun viewContext(): Context
    fun fragmentManager(): FragmentManager
}