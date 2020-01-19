package com.weather.weatherapp.di.module

import com.weather.weatherapp.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun contributeActivityAndroidInjector(): MainActivity
}