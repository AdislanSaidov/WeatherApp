package com.weather.weatherapp.di.module

import android.content.Context
import com.weather.weatherapp.data.datasource.Repository
import com.weather.weatherapp.data.models.Config
import com.weather.weatherapp.ui.home.HomePresenter
import com.weather.weatherapp.ui.home.WeatherDataMapper
import com.weather.weatherapp.ui.splash.SplashFragment
import com.weather.weatherapp.ui.splash.SplashPresenter
import com.weather.weatherapp.utils.MetricsConverter
import com.weather.weatherapp.utils.ResourceManager
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton
import com.weather.weatherapp.ui.splash.SplashNavigator as SplashNavigator

@Module
class AppModule {

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    fun provideResourceManager(context: Context): ResourceManager = ResourceManager(context)


}