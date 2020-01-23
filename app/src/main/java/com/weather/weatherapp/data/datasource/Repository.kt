package com.weather.weatherapp.data.datasource

import com.weather.weatherapp.data.datasource.local.PrefsManager
import com.weather.weatherapp.data.datasource.remote.RemoteDataSource
import com.weather.weatherapp.data.models.Config
import com.weather.weatherapp.data.models.ForecastData
import com.weather.weatherapp.data.models.WeatherData
import io.reactivex.Single
import retrofit2.Response

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val prefsManager: PrefsManager,
    private val config: Config
) {

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