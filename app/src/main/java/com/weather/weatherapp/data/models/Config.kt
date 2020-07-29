package com.weather.weatherapp.data.models

import com.weather.weatherapp.utils.Constants.DEFAULT_LANG
import com.weather.weatherapp.utils.Constants.DEFAULT_UNITS
import com.weather.weatherapp.utils.Constants.PRESSURE_METRIC_DEFAULT_VALUE
import com.weather.weatherapp.utils.Constants.TEMP_METRIC_DEFAULT_VALUE
import com.weather.weatherapp.utils.Constants.VISIBILITY_METRIC_DEFAULT_VALUE
import com.weather.weatherapp.utils.Constants.WIND_METRIC_DEFAULT_VALUE

class Config(
    var id: String = "",
    var apiKey: String = "",
    var units: String = DEFAULT_UNITS,
    var lang: String = DEFAULT_LANG,
    var visibilityMetric: Int = VISIBILITY_METRIC_DEFAULT_VALUE,
    var windMetric: Int = WIND_METRIC_DEFAULT_VALUE,
    var tempMetric: Int = TEMP_METRIC_DEFAULT_VALUE,
    var pressureMetric: Int = PRESSURE_METRIC_DEFAULT_VALUE,
    var coords: Coord = Coord()
)