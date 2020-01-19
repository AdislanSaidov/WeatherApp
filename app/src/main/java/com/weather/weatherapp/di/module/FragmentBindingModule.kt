package com.weather.weatherapp.di.module

import com.weather.weatherapp.ui.home.HomeFragment
import com.weather.weatherapp.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeFragment(): HomeFragment
}