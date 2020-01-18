package com.weather.weatherapp.di.component

import com.weather.weatherapp.di.module.AppModule
import com.weather.weatherapp.di.module.DataModule
import com.weather.weatherapp.ui.home.HomeFragment
import com.weather.weatherapp.ui.splash.SplashFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {
//    fun inject(activity: MainActivity)

    fun inject(fragment: HomeFragment)
    fun inject(fragment: SplashFragment)
}