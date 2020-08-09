package com.weather.weatherapp.di.module

import android.content.Context
import android.content.SharedPreferences
import com.weather.weatherapp.data.api.areas.AreasApiService
import com.weather.weatherapp.data.api.weather.WeatherApiService
import com.weather.weatherapp.data.api.weather.TokenInterceptor
import com.weather.weatherapp.data.datasource.Repository
import com.weather.weatherapp.data.datasource.SearchRepository
import com.weather.weatherapp.data.datasource.local.PrefsManager
import com.weather.weatherapp.data.datasource.remote.AreasRemoteDataSource
import com.weather.weatherapp.data.datasource.remote.WeatherRemoteDataSource
import com.weather.weatherapp.data.models.Config
import com.weather.weatherapp.utils.Constants.CITY_API_URL
import com.weather.weatherapp.utils.Constants.WEATHER_API_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule {


    @Provides
    @Singleton
    @Named("weather")
    fun provideWeatherRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(WEATHER_API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("city")
    fun provideCityRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(CITY_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(config: Config): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.i(message)
            }
        })
        val tokenInterceptor = TokenInterceptor(config.apiKey)
        val builder = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideSharedPrefsEditor(prefs: SharedPreferences): SharedPreferences.Editor {
        return prefs.edit()
    }

    @Provides
    @Singleton
    fun provideWeatherRetrofitService(@Named("weather") retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCityRetrofitService(@Named("city") retrofit: Retrofit): AreasApiService {
        return retrofit.create(AreasApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAreasRemoteDataSource(areasApiService: AreasApiService): AreasRemoteDataSource = AreasRemoteDataSource(areasApiService)

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: WeatherApiService): WeatherRemoteDataSource = WeatherRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: WeatherRemoteDataSource, prefsManager: PrefsManager, config: Config): Repository = Repository(remoteDataSource, prefsManager, config)

    @Provides
    @Singleton
    fun provideSearchRepository(remoteDataSource: AreasRemoteDataSource): SearchRepository = SearchRepository(remoteDataSource)


    @Provides
    @Singleton
    fun provideConfig(prefsManager: PrefsManager): Config = prefsManager.buildConfig()


    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)


    @Provides
    @Singleton
    fun providePrefsManager(prefs: SharedPreferences): PrefsManager = PrefsManager(prefs)
}