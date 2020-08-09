package com.weather.weatherapp.data.api.areas

import com.weather.weatherapp.data.models.Area
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface AreasApiService {

    @GET("search")
    fun areas(@Query("format") format: String = "json", @Query("city") city: String): Observable<List<Area>>


}
