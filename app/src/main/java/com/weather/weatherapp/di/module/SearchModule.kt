package com.weather.weatherapp.di.module

import com.weather.weatherapp.data.datasource.MainRepository
import com.weather.weatherapp.ui.search.SearchPresenter
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @Provides
    fun provideSearchPresenter(repository: MainRepository): SearchPresenter
            = SearchPresenter(repository)

}