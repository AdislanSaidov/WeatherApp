package com.weather.weatherapp.ui.home

import com.weather.weatherapp.domain.models.UiForecast
import com.weather.weatherapp.domain.models.UiWeatherData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType


interface HomeMvpView : MvpView{
    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showForecastData(forecasts: List<UiForecast>)

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showWeatherData(weatherData: UiWeatherData)

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showSunRising()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showFavorites()
}