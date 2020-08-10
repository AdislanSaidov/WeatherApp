package com.weather.weatherapp.di.module

import com.weather.weatherapp.data.ConfigManager
import com.weather.weatherapp.ui.settings.SettingsPresenter
import com.weather.weatherapp.utils.ResourceManager
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun provideSettingsPresenter(configManager: ConfigManager, resourceManager: ResourceManager) = SettingsPresenter(configManager, resourceManager)

}