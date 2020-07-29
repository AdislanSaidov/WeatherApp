package com.weather.weatherapp.ui.home

import com.weather.weatherapp.domain.models.UiForecast
import com.weather.weatherapp.domain.models.UiWeatherData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface HomeMvpView : MvpView{

    fun showForecastData(forecasts: List<UiForecast>)

    fun showWeatherData(weatherData: UiWeatherData)
}