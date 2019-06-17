package hu.tamasgomory.exchangerates.base

interface IPresenter {
    fun onCreate() {}
    fun onResume() {}
    fun onStart() {}
    fun onStop() {}
    fun onPause() {}
    fun onDestroy() {}
}