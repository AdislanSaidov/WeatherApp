package com.weather.weatherapp.data.datasource.local

import android.content.SharedPreferences
import com.weather.weatherapp.BuildConfig
import com.weather.weatherapp.data.models.Config
import com.weather.weatherapp.utils.Constants.PRESSURE_METRIC_DEFAULT_VALUE
import com.weather.weatherapp.utils.Constants.PRESSURE_METRIC_KEY
import com.weather.weatherapp.utils.Constants.TEMP_METRIC_DEFAULT_VALUE
import com.weather.weatherapp.utils.Constants.TEMP_METRIC_KEY
import com.weather.weatherapp.utils.Constants.VISIBILITY_METRIC_DEFAULT_VALUE
import com.weather.weatherapp.utils.Constants.VISIBILITY_METRIC_KEY
import com.weather.weatherapp.utils.Constants.WIND_METRIC_DEFAULT_VALUE
import com.weather.weatherapp.utils.Constants.WIND_METRIC_KEY
import java.util.*

class PrefsManager(
    private var prefs: SharedPreferences
) {


    private fun metricForVisibility(): Int = prefs.getInt(VISIBILITY_METRIC_KEY, VISIBILITY_METRIC_DEFAULT_VALUE)

    private fun metricForWind(): Int = prefs.getInt(WIND_METRIC_KEY, WIND_METRIC_DEFAULT_VALUE)

    private fun metricForTemp(): Int = prefs.getInt(TEMP_METRIC_KEY, TEMP_METRIC_DEFAULT_VALUE)

    private fun metricForPressure(): Int = prefs.getInt(PRESSURE_METRIC_KEY, PRESSURE_METRIC_DEFAULT_VALUE)

    fun buildConfig(): Config{
        val config = Config()
        config.lang = Locale.getDefault().language
        config.id = BuildConfig.CITY_ID
        config.apiKey = BuildConfig.API_KEY
        config.visibilityMetric = metricForVisibility()
        config.windMetric = metricForWind()
        config.tempMetric = metricForTemp()
        config.pressureMetric = metricForPressure()

        return config
    }

    fun saveTempUnit(temp: Int) {
        prefs.edit().putInt(TEMP_METRIC_KEY, temp).apply()
    }

    fun saveWindSpeedUnit(wind: Int) {
        prefs.edit().putInt(WIND_METRIC_KEY, wind).apply()
    }

    fun saveVisibilityUnit(visibility: Int) {
        prefs.edit().putInt(VISIBILITY_METRIC_KEY, visibility).apply()
    }

    fun savePressureUnit(pressure: Int) {
        prefs.edit().putInt(PRESSURE_METRIC_KEY, pressure).apply()
    }


}