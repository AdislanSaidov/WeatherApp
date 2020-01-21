package com.weather.weatherapp.data.datasource

import com.weather.weatherapp.data.datasource.local.PrefsManager
import com.weather.weatherapp.data.datasource.remote.RemoteDataSource
import com.weather.weatherapp.data.models.Config
import com.weather.weatherapp.data.models.Coordinates
import com.weather.weatherapp.data.models.Forecast
import com.weather.weatherapp.data.models.WeatherData
import io.reactivex.Single
import retrofit2.Response

class Repository(private val remoteDataSource: RemoteDataSource, private val prefsManager: PrefsManager, private val locationProvider: LocationProvider, private val config: Config) {

    fun fetchForecastData(): Single<Response<Forecast>> {
        return remoteDataSource.fetchForecastData(config.id, config.lang)
    }

    fun fetchWeatherData(): Single<WeatherData> {
        return remoteDataSource.fetchWeatherData(config.id, config.units, config.lang)
    }

    fun fetchCurrentCoordinates(): Coordinates?{
        return null
    }

}