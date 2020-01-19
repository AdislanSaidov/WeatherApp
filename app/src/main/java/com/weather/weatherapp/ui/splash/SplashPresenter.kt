package com.weather.weatherapp.ui.splash

import com.weather.weatherapp.data.datasource.Repository
import com.weather.weatherapp.ui.base.BasePresenter
import moxy.InjectViewState

@InjectViewState
class SplashPresenter(
    private val repository: Repository,
    private val splashNavigator: SplashNavigator
): BasePresenter<SplashMvpView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }


    fun onLocationKnown(){
        splashNavigator.navigateToMain()
    }



}