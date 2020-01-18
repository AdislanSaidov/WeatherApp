package com.weather.weatherapp.data.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class WeatherData (
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null,
    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null,
    @SerializedName("base")
    @Expose
    var base: String? = null,
    @SerializedName("main")
    @Expose
    var main: Main = Main(),
    @SerializedName("visibility")
    @Expose
    var visibility: Double = 0.0,
    @SerializedName("wind")
    @Expose
    var wind: Wind = Wind(),
    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null,
    @SerializedName("dt")
    @Expose
    var dt: Int? = null,
    @SerializedName("sys")
    @Expose
    var sys: Sys? = null,
    @SerializedName("timezone")
    @Expose
    var timezone: Int? = null,
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("cod")
    @Expose
    var cod: Int? = null,
    var units: String = "metric"

)