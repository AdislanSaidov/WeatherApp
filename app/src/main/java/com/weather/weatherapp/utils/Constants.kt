package com.weather.weatherapp.utils

object Constants {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY_QUERY_PARAM = "APPID"
    const val DEFAULT_UNITS = "metric"
    const val DEFAULT_LANG = "en"

    const val KM = 0
    const val METERS = 1
    const val MILES = 2

    const val CELSIUS = 0
    const val FAHRENHEIT = 1
    const val KELVIN = 2

    const val HPA = 0
    const val MBAR = 1
    const val MMHG = 2

    const val VISIBILITY_METRIC_KEY = "visibility_metric_key"
    const val VISIBILITY_METRIC_DEFAULT_VALUE = MILES
    const val WIND_METRIC_KEY = "wind_metric_key"
    const val WIND_METRIC_DEFAULT_VALUE = METERS

    const val TEMP_METRIC_KEY = "temp_metric_key"
    const val TEMP_METRIC_DEFAULT_VALUE = CELSIUS

    const val PRESSURE_METRIC_KEY = "pressure_metric_key"
    const val PRESSURE_METRIC_DEFAULT_VALUE = MBAR
}