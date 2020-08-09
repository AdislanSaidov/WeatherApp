package com.weather.weatherapp.data.datasource.remote

import com.weather.weatherapp.data.api.areas.AreasApiService
import com.weather.weatherapp.data.api.weather.WeatherApiService
import com.weather.weatherapp.data.models.Area
import com.weather.weatherapp.data.models.ForecastData
import com.weather.weatherapp.data.models.WeatherData
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

class AreasRemoteDataSource(private val apiService: AreasApiService) {

    fun areas(name: String): Observable<List<Area>> = apiService.areas(city = name)

}