package com.weather.weatherapp.data.datasource

import com.weather.weatherapp.data.ConfigManager
import com.weather.weatherapp.data.datasource.local.PrefsManager
import com.weather.weatherapp.data.datasource.remote.WeatherRemoteDataSource
import com.weather.weatherapp.data.models.Config
import com.weather.weatherapp.data.models.ForecastData
import com.weather.weatherapp.data.models.WeatherData
import io.reactivex.Single

class Repository(
        private val remoteDataSource: WeatherRemoteDataSource,
        configManager: ConfigManager
) {
    private val config = configManager.config

    fun fetchForecastData(): Single<ForecastData> {
        return remoteDataSource.fetchForecastDataByCoords(
            units = config.units, lang = config.lang,
            lat = config.coords.lat, lon = config.coords.lon
        )
    }

    fun fetchWeatherData(): Single<WeatherData> {
        return remoteDataSource.fetchWeatherDataByCoords(
            units = config.units, lang = config.lang,
            lat = config.coords.lat, lon = config.coords.lon
        )
    }


}