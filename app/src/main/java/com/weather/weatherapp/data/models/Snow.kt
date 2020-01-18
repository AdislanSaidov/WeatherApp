package com.weather.weatherapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Snow (
    @SerializedName("3h")
    @Expose
    var _3h: Double? = null
)