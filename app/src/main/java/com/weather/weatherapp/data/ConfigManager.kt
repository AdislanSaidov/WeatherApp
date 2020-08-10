package com.weather.weatherapp.data

import com.weather.weatherapp.data.datasource.local.PrefsManager
import com.weather.weatherapp.data.models.Config

class ConfigManager(private val prefsManager: PrefsManager) {

    val config: Config = prefsManager.buildConfig()

    fun saveTempUnit(temp: Int) {
        prefsManager.saveWindSpeedUnit(temp)
        config.tempMetric = temp
    }

    fun saveWindSpeedUnit(wind: Int) {
        prefsManager.saveWindSpeedUnit(wind)
        config.windMetric = wind
    }

    fun saveVisibilityUnit(visibility: Int) {
        prefsManager.saveWindSpeedUnit(visibility)
        config.visibilityMetric = visibility
    }

    fun savePressureUnit(pressure: Int) {
        prefsManager.saveWindSpeedUnit(pressure)
        config.pressureMetric = pressure
    }

}