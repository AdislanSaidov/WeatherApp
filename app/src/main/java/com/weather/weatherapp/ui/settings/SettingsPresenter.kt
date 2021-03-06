package com.weather.weatherapp.ui.settings

import com.weather.weatherapp.data.ConfigManager
import com.weather.weatherapp.ui.base.BasePresenter
import com.weather.weatherapp.utils.Constants.CELSIUS
import com.weather.weatherapp.utils.Constants.FAHRENHEIT
import com.weather.weatherapp.utils.Constants.HPA
import com.weather.weatherapp.utils.Constants.KELVIN
import com.weather.weatherapp.utils.Constants.KM
import com.weather.weatherapp.utils.Constants.KMPH
import com.weather.weatherapp.utils.Constants.MBAR
import com.weather.weatherapp.utils.Constants.METERS
import com.weather.weatherapp.utils.Constants.METERSPS
import com.weather.weatherapp.utils.Constants.MILES
import com.weather.weatherapp.utils.Constants.MILESPS
import com.weather.weatherapp.utils.Constants.MMHG
import com.weather.weatherapp.utils.ResourceManager
import moxy.InjectViewState

@InjectViewState
class SettingsPresenter(
    private val configManager: ConfigManager,
    private val resourceManager: ResourceManager
) : BasePresenter<SettingsMvpView>() {

    private val config = configManager.config

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        fetchCurrentConfig()
    }

    private fun fetchCurrentConfig() {

        val currentTempMetric = when (config.tempMetric) {
            CELSIUS -> resourceManager.tempCelsius()
            FAHRENHEIT -> resourceManager.tempFahrenheit()
            KELVIN -> resourceManager.tempKelvin()
            else -> ""
        }

        val currentPressureMetric = when (config.pressureMetric) {
            HPA -> resourceManager.pressureHpa()
            MBAR -> resourceManager.pressureMbar()
            MMHG -> resourceManager.pressureMmhg()
            else -> ""
        }

        val currentVisibilityMetric = when (config.visibilityMetric) {
            KM -> resourceManager.visibilityKm()
            METERS -> resourceManager.visibilityMeters()
            MILES -> resourceManager.visibilityMiles()
            else -> ""
        }

        val currentWindMetric = when (config.windMetric) {
            KMPH -> resourceManager.speedKmPerHour()
            METERSPS -> resourceManager.speedMetersPerSecond()
            MILESPS -> resourceManager.speedMilesPerHour()
            else -> ""
        }

        viewState.showCurrentUnits(
            Settings(
                temp = currentTempMetric,
                pressure = currentPressureMetric,
                visibility = currentVisibilityMetric,
                wind = currentWindMetric
            )
        )
    }

    fun fetchCurrentTempUnit() {
        viewState.showCurrentTempUnit(config.tempMetric)
    }

    fun fetchCurrentWindSpeedUnit() {
        viewState.showCurrentWindSpeedUnit(config.windMetric)
    }

    fun fetchCurrentVisibilityUnit() {
        viewState.showCurrentVisibilityUnit(config.visibilityMetric)
    }

    fun fetchCurrentPressureUnit() {
        viewState.showCurrentPressureUnit(config.pressureMetric)
    }

    fun saveTempChoice(temp: Int) {
        configManager.saveTempUnit(temp)
        fetchCurrentConfig()
    }

    fun saveWindSpeedChoice(wind: Int) {
        configManager.saveWindSpeedUnit(wind)
        fetchCurrentConfig()
    }

    fun saveVisibilityChoice(visibility: Int) {
        configManager.saveVisibilityUnit(visibility)
        fetchCurrentConfig()
    }

    fun savePressureChoice(pressure: Int) {
        configManager.savePressureUnit(pressure)
        fetchCurrentConfig()
    }

}