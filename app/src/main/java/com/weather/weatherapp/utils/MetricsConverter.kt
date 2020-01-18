package com.weather.weatherapp.utils

import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject
import kotlin.math.roundToInt

inline class Meters(val value: Double)

inline class KiloMeters(val value: Double)

inline class Miles(val value: Double)

inline class Celsius(val value: Double)

inline class Hpa(val value: Int)


class MetricsConverter @Inject constructor() {

    fun kmToMiles(km: Double): Int = (km * 1.60934).roundToInt()

    fun toMiles(meters: Meters): Int = (meters.value * 0.000621371).roundToInt()

    fun toMiles(kiloMeters: KiloMeters): Int = (kiloMeters.value * 1.60934).roundToInt()

    fun toKm(miles: Miles): Int = (miles.value / 1.60934).roundToInt()

    fun toKm(meters: Meters): Int = (meters.value / 1000).roundToInt()

    fun toMetersPerSecond(kiloMeters: KiloMeters): Int =
        ((kiloMeters.value * 1000) / (60 * 60)).roundToInt()

    fun toFahrenheit(celsius: Celsius): Int = (celsius.value * 1.8 + 32).roundToInt()

    fun toMbar(hpa: Hpa): Int = (0.01 * hpa.value).roundToInt()

    fun toMmhg(hpa: Hpa): Double =
        BigDecimal(0.00750062 * hpa.value).setScale(2, RoundingMode.HALF_UP).toDouble()
}