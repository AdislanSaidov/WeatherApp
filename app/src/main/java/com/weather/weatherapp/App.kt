package com.weather.weatherapp

import android.app.Application
import com.weather.weatherapp.di.component.AppComponent
import com.weather.weatherapp.di.component.DaggerAppComponent
import com.weather.weatherapp.di.module.AppModule
import com.weather.weatherapp.di.module.DataModule
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    companion object{
        lateinit var appComponent: AppComponent
    }


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .dataModule(DataModule())
            .build()
    }

}