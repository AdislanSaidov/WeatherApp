package com.weather.weatherapp.domain.models

import com.weather.weatherapp.data.models.Clouds
import com.weather.weatherapp.data.models.Coord
import com.weather.weatherapp.data.models.Sys
import com.weather.weatherapp.data.models.Weather

data class UiWeatherData (

        var humidity: String = "",
        var feelsLike: String = "",
        var temp: String = "",
        var pressure: String = "",
        var wind: String = "",
        var coord: Coord? = null,
        var weather: Weather,
        var base: String? = null,
        var visibility: String = "",
        var clouds: Clouds? = null,
        var dt: Int? = null,
        var sys: Sys? = null,
        var timezone: Int? = null,
        var id: Int? = null,
        var name: String? = null,
        var cod: Int? = null,
        var sunRise: String = "",
        var sunSet: String = "",
        var currentMinute: Int = 0,
        var minutesBetween: Int = 0
)