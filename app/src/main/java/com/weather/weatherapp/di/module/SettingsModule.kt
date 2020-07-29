package com.weather.weatherapp.di.module

import com.weather.weatherapp.data.datasource.Repository
import com.weather.weatherapp.data.datasource.local.PrefsManager
import com.weather.weatherapp.ui.settings.SettingsPresenter
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun provideSettingsPresenter(repository: Repository, prefsManager: PrefsManager): SettingsPresenter
            = SettingsPresenter(repository, prefsManager)

}