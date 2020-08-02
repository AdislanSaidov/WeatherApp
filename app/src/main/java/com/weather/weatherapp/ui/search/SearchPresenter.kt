package com.weather.weatherapp.ui.search

import com.weather.weatherapp.data.datasource.MainRepository
import com.weather.weatherapp.ui.base.BasePresenter
import moxy.InjectViewState

@InjectViewState
class SearchPresenter(private val repository: MainRepository) : BasePresenter<SearchMvp>() {


}