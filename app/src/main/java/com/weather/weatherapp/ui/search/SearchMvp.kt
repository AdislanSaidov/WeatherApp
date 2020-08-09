package com.weather.weatherapp.ui.search

import com.weather.weatherapp.data.models.Area
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface SearchMvp: MvpView {
    fun showAreas(areas: List<Area>)
    fun showNotFound()
}