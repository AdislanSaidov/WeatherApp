package com.weather.weatherapp.ui.home

import com.weather.weatherapp.data.models.*
import com.weather.weatherapp.domain.models.UiForecast
import com.weather.weatherapp.domain.models.UiWeatherData
import com.weather.weatherapp.utils.*
import com.weather.weatherapp.utils.Constants.CELSIUS
import com.weather.weatherapp.utils.Constants.FAHRENHEIT
import com.weather.weatherapp.utils.Constants.HPA
import com.weather.weatherapp.utils.Constants.KM
import com.weather.weatherapp.utils.Constants.MBAR
import com.weather.weatherapp.utils.Constants.METERS
import com.weather.weatherapp.utils.Constants.MILES
import com.weather.weatherapp.utils.Constants.MMHG
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class WeatherDataMapper(
        private val resourceManager: ResourceManager,
        private val metricsConverter: MetricsConverter,
        private val config: Config
) {

    fun toUiModel(weatherData: WeatherData): UiWeatherData {

        val weather = weatherData.weather?.get(0) ?: Weather()
        weather.icon = "http://openweathermap.org/img/wn/${weather.icon}@2x.png"
        weather.description = weather.description.capitalize()
        val sdf = SimpleDateFormat("HH:mm", Locale.ROOT)
        val sunRiseDate = Date((weatherData.sys?.sunRise ?: 0) * 1000)
        val sunSetDate = Date((weatherData.sys?.sunSet ?: 0) * 1000)
        val calendar = GregorianCalendar.getInstance()
        calendar.time = sunRiseDate
        return UiWeatherData(
                temp = convertTemp(weatherData.main.temp),
                feelsLike = convertTemp(weatherData.main.feelsLike),
                weather = weather,
                wind = convertWind(weatherData.wind),
                pressure = convertPressure(weatherData.main.pressure),
                visibility = convertVisibility(weatherData.visibility),
                name = weatherData.name,
                humidity = "${weatherData.main.humidity} %",
                sunRise = sdf.format(sunRiseDate),
                sunSet = sdf.format(sunSetDate),
                sunHours = ((sunSetDate.time - sunRiseDate.time) / (1000 * 60 * 60)).toInt(),
                currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                sunRiseHour = calendar.get(Calendar.HOUR_OF_DAY)
        )
    }

    fun toUiModel(forecastData: ForecastData): List<UiForecast> {
        val forecasts = ArrayList<UiForecast>()
        val dayFormat = SimpleDateFormat("HH:mm\n", Locale.ROOT)
        val tomorrowFormat = SimpleDateFormat("HH:mm\nd MMM", Locale(Locale.getDefault().language))
        val timeZone = TimeZone.getTimeZone("UTC")
        tomorrowFormat.timeZone = timeZone
        dayFormat.timeZone = timeZone
        val date = Date()
        val currentTimeMillis = System.currentTimeMillis()
        forecastData.list.forEach { fd ->
            val weather = fd.weather?.get(0) ?: Weather()

            date.time = (fd.dt * 1000)
            forecasts.add(
                    UiForecast(
                            temp = convertTemp(fd.main.temp),
                            dt = if (isTomorrow(date.time, currentTimeMillis, timeZone)) tomorrowFormat.format(date) else dayFormat.format(date),
                            weather = weather,
                            icon = resourceManager.getIconId(weather.icon)
                    )
            )
        }

        return forecasts
    }


    private fun isTomorrow(t: Long, currentTimeMillis: Long, timeZone: TimeZone): Boolean {
        val now = Calendar.getInstance()
        val forecastDate = Calendar.getInstance()
        forecastDate.timeInMillis = t
        now.timeInMillis = currentTimeMillis
        forecastDate.timeZone = timeZone

        return !(now.get(Calendar.YEAR) == forecastDate.get(Calendar.YEAR) &&
                now.get(Calendar.MONTH) == forecastDate.get(Calendar.MONTH) &&
                now.get(Calendar.DATE) == forecastDate.get(Calendar.DATE)) &&
                forecastDate.get(Calendar.HOUR) == 0 &&
                forecastDate.get(Calendar.MINUTE) == 0 &&
                forecastDate.get(Calendar.AM_PM) == Calendar.AM
    }


    private fun convertPressure(pressure: Int): String {
        return when (config.pressureMetric) {
            MBAR -> "${metricsConverter.toMbar(Hpa(pressure))} ${resourceManager.pressureMbar()}"
            MMHG -> "${metricsConverter.toMmhg(Hpa(pressure))} ${resourceManager.pressureMmhg()}"
            HPA -> "$pressure ${resourceManager.pressureHpa()}"
            else -> ""
        }
    }

    private fun convertVisibility(visibility: Double): String {
        return when (config.visibilityMetric) {
            KM -> "${metricsConverter.toKm(Meters(visibility))} ${resourceManager.visibilityKm()}"
            MILES -> "${metricsConverter.toMiles(Meters(visibility))} ${resourceManager.visibilityMiles()}"
            else -> ""
        }
    }

    private fun convertTemp(temp: Double): String {
        val tempData = when (config.tempMetric) {
            FAHRENHEIT -> metricsConverter.toFahrenheit(Celsius(temp))
            CELSIUS -> temp.roundToInt()
            else -> 0
        }
        return when {
            tempData > 0 -> "+$tempData °"
            else -> "$tempData °"
        }
    }

    private fun convertWind(wind: Wind): String {
        return when (config.windMetric) {
            KM -> "${wind.speed.roundToInt()} ${resourceManager.speedKmPerHour()}"
            MILES -> "${metricsConverter.toMiles(KiloMeters(wind.speed))} ${resourceManager.speedMilesPerHour()}"
            METERS -> "${metricsConverter.toMetersPerSecond(KiloMeters(wind.speed))} ${resourceManager.speedMetersPerSecond()}"
            else -> ""
        } + " ${degreesToWeatherDirectionName(wind.deg)}"
    }

    private fun degreesToWeatherDirectionName(degree: Int): String {
        if (degree > 337.5) return resourceManager.northerly()
        if (degree > 292.5) return resourceManager.northWesterly()
        if (degree > 247.5) return resourceManager.westerly()
        if (degree > 202.5) return resourceManager.southWesterly()
        if (degree > 157.5) return resourceManager.southerly()
        if (degree > 122.5) return resourceManager.southEasterly()
        if (degree > 67.5) return resourceManager.easterly()
        if (degree > 22.5) return resourceManager.northEasterly()
        return resourceManager.northerly()
    }
}