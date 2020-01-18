package com.weather.weatherapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Wind (
    @SerializedName("speed")
    @Expose
    var speed: Double = 0.0,
    @SerializedName("deg")
    @Expose
    var deg: Int = 0

)