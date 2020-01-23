package com.weather.weatherapp.data.datasource

import com.weather.weatherapp.data.datasource.local.PrefsManager
import com.weather.weatherapp.data.datasource.remote.RemoteDataSource
import com.weather.weatherapp.data.models.*
import com.weather.weatherapp.ui.main.OnLocationListener
import io.reactivex.Single
import retrofit2.Response

class MainRepository(private val prefsManager: PrefsManager, private val locationProvider: LocationProvider, private val config: Config) : OnLocationListener {

    private var onLocationListener: OnLocationListener? = null


    fun requestCurrentCoordinates(){
        return locationProvider.requestLocation()
    }

    fun setOnLocationListener(onLocationListener: OnLocationListener){
        this.onLocationListener = onLocationListener
        locationProvider.setOnLocationListener(this)
    }

    override fun onLocationKnown(coord: Coord) {
        config.coords = coord.copy()
        onLocationListener?.onLocationKnown(coord)
    }


}