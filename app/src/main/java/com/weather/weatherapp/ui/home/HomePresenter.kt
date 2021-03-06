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
        fetchData()
    }

    fun fetchData(){
        fetchWeatherData()
        fetchForecastData()
    }

    private fun fetchForecastData(){
        disposable.add(repository.fetchForecastData()
            .compose(RxUtils.singleAsync())
            .subscribe({ forecastData ->
                val forecasts = weatherDataMapper.toUiModel(forecastData)
                viewState.showForecastData(forecasts)
            }, Timber::e))
    }

    private fun fetchWeatherData(){
        disposable.add(repository.fetchWeatherData()
            .compose(RxUtils.singleAsync())
            .subscribe({weatherData ->
                val convertedModel = weatherDataMapper.toUiModel(weatherData)
                viewState.showWeatherData(convertedModel)
                viewState.showSunRising()
            }, Timber::e)
        )
    }


}