package com.weather.weatherapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coord (
    @SerializedName("lat")
    @Expose
    var lat: Double? = null,
    @SerializedName("lon")
    @Expose
    var lon: Double? = null
)