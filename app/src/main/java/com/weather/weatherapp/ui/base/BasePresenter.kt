package com.weather.weatherapp.ui.base

import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import moxy.MvpView


open class BasePresenter<T: MvpView> : MvpPresenter<T>() {

    val disposable = CompositeDisposable()



}