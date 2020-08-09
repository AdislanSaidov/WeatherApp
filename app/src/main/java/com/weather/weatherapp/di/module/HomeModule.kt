package com.weather.weatherapp.di.module

import com.weather.weatherapp.data.datasource.Repository
import com.weather.weatherapp.data.models.Config
import com.weather.weatherapp.ui.home.ForecastAdapter
import com.weather.weatherapp.ui.home.HomePresenter
import com.weather.weatherapp.ui.home.WeatherDataMapper
import com.weather.weatherapp.utils.MetricsConverter
import com.weather.weatherapp.utils.ResourceManager
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun provideWeatherDataMapper(resourceManager: ResourceManager, metricsConverter: MetricsConverter, config: Config): WeatherDataMapper
            = WeatherDataMapper(
        resourceManager,
        metricsConverter,
        config
    )

    @Provides
    fun provideHomePresenter(repository: Repository, weatherDataMapper: WeatherDataMapper): HomePresenter = HomePresenter(repository, weatherDataMapper)

    @Provides
    fun provideForecastAdapter(): ForecastAdapter = ForecastAdapter()
}