package com.weather.weatherapp.di.module

import com.weather.weatherapp.data.datasource.SearchRepository
import com.weather.weatherapp.ui.search.SearchPresenter
import com.weather.weatherapp.ui.search.SearchResultAdapter
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @Provides
    fun provideSearchPresenter(repository: SearchRepository) = SearchPresenter(repository)

    @Provides
    fun provideSearchResultAdapter() = SearchResultAdapter()
}