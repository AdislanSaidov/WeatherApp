package com.weather.weatherapp.di.module

import com.weather.weatherapp.data.ConfigManager
import com.weather.weatherapp.data.datasource.MainRepository
import com.weather.weatherapp.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainPresenter(repository: MainRepository) = MainPresenter(repository)

    @Provides
    fun provideMainRepository(configManager: ConfigManager) = MainRepository(configManager)


}