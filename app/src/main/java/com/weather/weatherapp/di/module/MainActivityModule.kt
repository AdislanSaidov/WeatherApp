package com.weather.weatherapp.di.module

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.weather.weatherapp.data.datasource.LocationProvider
import com.weather.weatherapp.data.datasource.MainRepository
import com.weather.weatherapp.data.datasource.Repository
import com.weather.weatherapp.data.datasource.local.PrefsManager
import com.weather.weatherapp.data.datasource.remote.RemoteDataSource
import com.weather.weatherapp.data.models.Config
import com.weather.weatherapp.ui.home.HomePresenter
import com.weather.weatherapp.ui.home.WeatherDataMapper
import com.weather.weatherapp.ui.main.MainActivity
import com.weather.weatherapp.ui.main.MainPresenter
import com.weather.weatherapp.utils.MetricsConverter
import com.weather.weatherapp.utils.ResourceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainActivityModule {


    @Provides
    fun provideMainPresenter(repository: MainRepository): MainPresenter = MainPresenter(repository)

    @Provides
    fun provideFusedLocationProviderClient(mainActivity: MainActivity): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mainActivity)

    @Provides
    fun provideLocationProvider(fusedLocationProviderClient: FusedLocationProviderClient): LocationProvider = LocationProvider(fusedLocationProviderClient)

    @Provides
    fun provideMainRepository(remoteDataSource: RemoteDataSource, prefsManager: PrefsManager, locationProvider: LocationProvider, config: Config): MainRepository = MainRepository(prefsManager, locationProvider, config)


}