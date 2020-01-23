package com.weather.weatherapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Forecast (
    @SerializedName("dt")
    @Expose
    var dt: Int = 0,
    @SerializedName("main")
    @Expose
    var main: Main = Main(),
    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null,
    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null,
    @SerializedName("wind")
    @Expose
    var wind: Wind? = null,
    @SerializedName("snow")
    @Expose
    var snow: Snow? = null,
    @SerializedName("sys")
    @Expose
    var sys: Sys? = null,
    @SerializedName("dt_txt")
    @Expose
    var dtTxt: String? = null
)