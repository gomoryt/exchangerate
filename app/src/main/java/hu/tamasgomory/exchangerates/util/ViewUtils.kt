package hu.tamasgomory.exchangerates.util

import android.view.View

object ViewUtils {
    fun toggle(view: View, visibility: Boolean) {
        if (visibility) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}