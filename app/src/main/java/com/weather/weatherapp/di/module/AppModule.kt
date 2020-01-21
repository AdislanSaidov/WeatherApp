package com.weather.weatherapp.di.module

import android.content.Context
import com.weather.weatherapp.utils.ResourceManager
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

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