package com.weather.weatherapp.utils

import android.content.Context
import com.weather.weatherapp.R

class ResourceManager(private val context: Context) {
    fun northerly(): String = context.getString(R.string.northerly)
    fun northWesterly(): String = context.getString(R.string.north_westerly)
    fun westerly(): String = context.getString(R.string.westerly)
    fun southWesterly(): String = context.getString(R.string.south_westerly)
    fun southerly(): String = context.getString(R.string.southerly)
    fun southEasterly(): String = context.getString(R.string.south_easterly)
    fun easterly(): String = context.getString(R.string.easterly)
    fun northEasterly(): String = context.getString(R.string.north_easterly)

    fun speedKmPerHour(): String = context.getString(R.string.km_per_hour)
    fun speedMilesPerHour(): String = context.getString(R.string.miles_per_hour)
    fun speedMetersPerSecond(): String = context.getString(R.string.meters_per_second)

    fun pressureMmhg(): String = context.getString(R.string.mmhg)
    fun pressureHpa(): String = context.getString(R.string.hpa)
    fun pressureMbar(): String = context.getString(R.string.mbar)

    fun visibilityKm(): String = context.getString(R.string.km)
    fun visibilityMiles(): String = context.getString(R.string.miles)

}