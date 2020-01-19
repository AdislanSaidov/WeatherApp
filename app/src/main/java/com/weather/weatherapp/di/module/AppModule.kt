package com.weather.weatherapp.di.module

import android.content.Context
import com.weather.weatherapp.App
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
    fun provideSimpleDateFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ROOT)
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    fun provideResourceManager(context: Context): ResourceManager = ResourceManager(context)

    @Provides
    fun provideWeatherDataMapper(resourceManager: ResourceManager, metricsConverter: MetricsConverter, config: Config): WeatherDataMapper
            = WeatherDataMapper(
        resourceManager,
        metricsConverter,
        config
    )

    @Provides
    fun provideHomePresenter(repository: Repository, weatherDataMapper: WeatherDataMapper): HomePresenter = HomePresenter(repository, weatherDataMapper)

//    @Provides
//    fun provideSplashNavigator(splashFragment: SplashFragment): SplashNavigator = splashFragment

    @Provides
    fun provideSplashPresenter(repository: Repository): SplashPresenter = SplashPresenter(repository)
//    fun provideSplashPresenter(repository: Repository, splashNavigator: SplashNavigator): SplashPresenter = SplashPresenter(repository, splashNavigator)

}