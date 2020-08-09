package com.weather.weatherapp.data.datasource.remote

import com.weather.weatherapp.data.api.areas.AreasApiService
import com.weather.weatherapp.data.models.Area
import io.reactivex.Observable

class AreasRemoteDataSource(private val apiService: AreasApiService) {

    fun areas(name: String): Observable<List<Area>> = apiService.areas(city = name)

}