package com.weather.weatherapp.ui.home

import com.weather.weatherapp.domain.models.UiWeatherData
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface HomeMvpView : MvpView{

    fun showForecastData()

    fun showWeatherData(weatherData: UiWeatherData)
}