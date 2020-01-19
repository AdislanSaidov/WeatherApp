package com.weather.weatherapp.di.module

import com.weather.weatherapp.data.datasource.Repository
import com.weather.weatherapp.ui.splash.SplashFragment
import com.weather.weatherapp.ui.splash.SplashNavigator
import com.weather.weatherapp.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @Provides
    fun provideSplashNavigator(splashFragment: SplashFragment): SplashNavigator = splashFragment

    @Provides
    fun provideSplashPresenter(repository: Repository, splashNavigator: SplashNavigator): SplashPresenter = SplashPresenter(repository, splashNavigator)
}