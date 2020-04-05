package com.weather.weatherapp.data.datasource.remote

import com.weather.weatherapp.data.api.ApiService
import com.weather.weatherapp.data.models.ForecastData
import com.weather.weatherapp.data.models.WeatherData
import io.reactivex.Single
import retrofit2.Response

class RemoteDataSource(private val apiService: ApiService) {


    fun fetchForecastData(id: String, lang: String): Single<Response<ForecastData>> = apiService.forecast(id, lang)

    fun fetchForecastDataByCoords(units: String, lang: String, lat: Double, lon: Double): Single<ForecastData> = apiService.forecast(units, lang, lat, lon)

    fun fetchWeatherData(id: String, units: String, lang: String): Single<WeatherData> = apiService.weather(id, units, lang)

    fun fetchWeatherDataByCoords(units: String, lang: String, lat: Double, lon: Double): Single<WeatherData> = apiService.weather(units, lang, lat, lon)

}