package com.weather.weatherapp.data.datasource

import com.weather.weatherapp.data.datasource.local.PrefsManager
import com.weather.weatherapp.data.models.Config
import com.weather.weatherapp.data.models.Coord

class MainRepository(private val prefsManager: PrefsManager,  private val config: Config) {

    fun storeLocation(coord: Coord){
        config.coords = coord
    }

}