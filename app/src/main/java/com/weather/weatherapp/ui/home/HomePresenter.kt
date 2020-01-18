package com.weather.weatherapp.ui.home

import com.weather.weatherapp.data.datasource.Repository
import com.weather.weatherapp.ui.base.BasePresenter
import com.weather.weatherapp.utils.RxUtils
import moxy.InjectViewState
import timber.log.Timber

@InjectViewState
class HomePresenter(private val repository: Repository, private val weatherDataMapper: WeatherDataMapper): BasePresenter<HomeMvpView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        fetchWeatherData()
    }

    fun fetchForecastData(){
        disposable.add(repository.fetchForecastData()
            .compose(RxUtils.singleAsync())
            .subscribe())
    }

    private fun fetchWeatherData(){
        disposable.add(repository.fetchWeatherData()
            .compose(RxUtils.singleAsync())
            .subscribe({weatherData ->
                val convertedModel = weatherDataMapper.toUiModel(weatherData)
                viewState.showWeatherData(convertedModel)}, Timber::e)
        )
    }


}