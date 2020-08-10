package com.weather.weatherapp.data.datasource

import com.weather.weatherapp.data.ConfigManager
import com.weather.weatherapp.data.models.Coord

class MainRepository(private val configManager: ConfigManager) {

    fun storeLocation(coord: Coord){
        configManager.config.coords = coord
    }

}