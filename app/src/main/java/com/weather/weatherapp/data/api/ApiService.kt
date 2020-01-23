package com.weather.weatherapp.data.api

import com.weather.weatherapp.data.models.Forecast
import com.weather.weatherapp.data.models.WeatherData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("forecast")
    fun forecast(@Query("id") id: String, @Query("lang") lang: String): Single<Response<Forecast>>

    @GET("weather")
    fun weather(@Query("id") id: String, @Query("units") units: String, @Query("lang") lang: String): Single<WeatherData>

    @GET("weather")
    fun weather(@Query("units") units: String, @Query("lang") lang: String, @Query("lat") lat: Double, @Query("lon") lon: Double): Single<WeatherData>
}
