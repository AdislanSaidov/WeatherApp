package com.weather.weatherapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class City (
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null,
    @SerializedName("country")
    @Expose
    var country: String? = null,
    @SerializedName("timezone")
    @Expose
    var timezone: Int? = null,
    @SerializedName("sunrise")
    @Expose
    var sunrise: Int? = null,
    @SerializedName("sunset")
    @Expose
    var sunset: Int? = null

)