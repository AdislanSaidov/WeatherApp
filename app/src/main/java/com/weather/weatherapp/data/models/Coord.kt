package com.weather.weatherapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coord (
    @SerializedName("lat")
    @Expose
    var lat: Double = 0.0,
    @SerializedName("lon")
    @Expose
    var lon: Double = 0.0
)