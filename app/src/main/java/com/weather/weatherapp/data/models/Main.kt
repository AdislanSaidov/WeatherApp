package com.weather.weatherapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    @Expose
    var temp: Double = 0.0,
    @SerializedName("feels_like")
    @Expose
    var feelsLike: Double = 0.0,
    @SerializedName("temp_min")
    @Expose
    var tempMin: String? = null,
    @SerializedName("temp_max")
    @Expose
    var tempMax: String? = null,
    @SerializedName("pressure")
    @Expose
    var pressure: Int = 0,
    @SerializedName("sea_level")
    @Expose
    var seaLevel: Int? = null,
    @SerializedName("grnd_level")
    @Expose
    var grndLevel: Int? = null,
    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null,
    @SerializedName("temp_kf")
    @Expose
    var tempKf: Int? = null

)