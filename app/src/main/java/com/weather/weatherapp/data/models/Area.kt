package com.weather.weatherapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Area(
        @SerializedName("display_name")
        @Expose
        val name: String,
        @SerializedName("icon")
        @Expose
        val icon: String,
        @SerializedName("importance")
        @Expose
        val importance: Double,
        @SerializedName("lat")
        @Expose
        val lat: String,
        @SerializedName("licence")
        @Expose
        val licence: String,
        @SerializedName("lon")
        @Expose
        val lon: String,
        @SerializedName("osm_id")
        @Expose
        val osmId: String,
        @SerializedName("osm_type")
        @Expose
        val osmType: String,
        @SerializedName("place_id")
        @Expose
        val placeId: String,
        @SerializedName("type")
        @Expose
        val type: String
)