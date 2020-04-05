package com.weather.weatherapp.ui.main

import com.weather.weatherapp.data.datasource.MainRepository
import com.weather.weatherapp.data.models.Coord
import com.weather.weatherapp.ui.base.BasePresenter
import moxy.InjectViewState

@InjectViewState
class MainPresenter(private val repository: MainRepository) : BasePresenter<MainMvpView>() {


    fun onLocationKnown(lat: Double, lon: Double) {
        repository.storeLocation(coord = Coord(lat = lat, lon = lon))
        viewState.showHomeScreen()
    }

}