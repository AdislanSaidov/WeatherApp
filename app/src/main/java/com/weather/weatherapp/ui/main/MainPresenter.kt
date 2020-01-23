package com.weather.weatherapp.ui.main

import com.weather.weatherapp.data.datasource.MainRepository
import com.weather.weatherapp.data.models.Coord
import com.weather.weatherapp.ui.base.BasePresenter
import moxy.InjectViewState

@InjectViewState
class MainPresenter(private val repository: MainRepository): BasePresenter<MainMvpView>(), OnLocationListener {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        repository.setOnLocationListener(this)
        repository.requestCurrentCoordinates()
    }

    fun onLocationPermissionGranted(){

    }

    override fun onLocationKnown(coord: Coord) {

        viewState.showHomeScreen()
    }

}