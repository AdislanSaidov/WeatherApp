package com.weather.weatherapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Sys (
    @SerializedName("pod")
    @Expose
    var pod: String? = null,
    @SerializedName("sunrise")
    @Expose
    var sunRise: Long = 0,
    @SerializedName("sunset")
    @Expose
    var sunSet: Long = 0
)