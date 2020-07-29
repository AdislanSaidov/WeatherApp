package com.weather.weatherapp.di.module

import com.weather.weatherapp.ui.home.HomeFragment
import com.weather.weatherapp.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun contributeSettingsFragment(): SettingsFragment
}