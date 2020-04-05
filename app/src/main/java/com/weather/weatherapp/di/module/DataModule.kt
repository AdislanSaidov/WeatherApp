package com.weather.weatherapp.di.module

import android.content.Context
import android.content.SharedPreferences
import com.weather.weatherapp.data.api.ApiService
import com.weather.weatherapp.data.api.TokenInterceptor
import com.weather.weatherapp.data.datasource.Repository
import com.weather.weatherapp.data.datasource.local.PrefsManager
import com.weather.weatherapp.data.datasource.remote.RemoteDataSource
import com.weather.weatherapp.data.models.Config
import com.weather.weatherapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(config: Config): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message: String -> Timber.i(message) }
        )
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
    fun provideRetrofitService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource = RemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource, prefsManager: PrefsManager, config: Config): Repository = Repository(remoteDataSource, prefsManager, config)

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