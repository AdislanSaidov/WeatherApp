package com.weather.weatherapp.di.module

import com.weather.weatherapp.data.datasource.MainRepository
import com.weather.weatherapp.data.datasource.local.PrefsManager
import com.weather.weatherapp.data.datasource.remote.WeatherRemoteDataSource
import com.weather.weatherapp.data.models.Config
import com.weather.weatherapp.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainPresenter(repository: MainRepository): MainPresenter = MainPresenter(repository)

    @Provides
    fun provideMainRepository(remoteDataSource: WeatherRemoteDataSource, prefsManager: PrefsManager, config: Config): MainRepository = MainRepository(prefsManager, config)


}