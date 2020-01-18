package com.weather.weatherapp.ui.splash

import com.weather.weatherapp.data.datasource.Repository
import com.weather.weatherapp.ui.base.BasePresenter
import com.weather.weatherapp.ui.home.HomeMvpView
import moxy.InjectViewState

@InjectViewState
class SplashPresenter(private val repository: Repository): BasePresenter<SplashMvpView>() {
    private lateinit var splashNavigator: SplashNavigator
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun setNavigator(splashNavigator: SplashNavigator){
        this.splashNavigator = splashNavigator
    }

    fun onLocationKnown(){
        splashNavigator.navigateToMain()
    }



}