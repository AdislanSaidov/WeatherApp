package com.weather.weatherapp.ui.settings

import com.weather.weatherapp.data.datasource.Repository
import com.weather.weatherapp.data.datasource.local.PrefsManager
import com.weather.weatherapp.data.models.Config
import com.weather.weatherapp.ui.base.BasePresenter
import moxy.InjectViewState

@InjectViewState
class SettingsPresenter(private val repository: Repository, private val prefsManager: PrefsManager): BasePresenter<SettingsMvpView>() {

    private lateinit var config: Config

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        fetchCurrentConfig()
    }

    private fun fetchCurrentConfig(){
        config = prefsManager.buildConfig()
        viewState.showCurrentUnits()
    }

    fun fetchCurrentTempUnit(){
        viewState.showCurrentTempUnit(config.tempMetric)
    }

    fun fetchCurrentWindSpeedUnit(){
        viewState.showCurrentWindSpeedUnit(config.windMetric)
    }

    fun fetchCurrentVisibilityUnit(){
        viewState.showCurrentVisibilityUnit(config.visibilityMetric)
    }

    fun fetchCurrentPressureUnit(){
        viewState.showCurrentPressureUnit(config.pressureMetric)
    }

    fun saveTempChoice(temp: Int) {
        config.tempMetric = temp
        prefsManager.saveTempUnit(temp)
    }

    fun saveWindSpeedChoice(wind: Int) {
        config.windMetric = wind
        prefsManager.saveWindSpeedUnit(wind)
    }

    fun saveVisibilityChoice(visibility: Int) {
        config.visibilityMetric = visibility
        prefsManager.saveVisibilityUnit(visibility)
    }

    fun savePressureChoice(pressure: Int) {
        config.pressureMetric = pressure
        prefsManager.savePressureUnit(pressure)
    }

}