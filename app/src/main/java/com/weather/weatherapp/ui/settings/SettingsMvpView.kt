package com.weather.weatherapp.ui.settings

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface SettingsMvpView : MvpView{
    fun showCurrentTempUnit(unit: Int)

    fun showCurrentVisibilityUnit(unit: Int)

    fun showCurrentWindSpeedUnit(unit: Int)

    fun showCurrentPressureUnit(unit: Int)

    fun showCurrentUnits(settings: Settings)

}