package com.weather.weatherapp.data.datasource

import com.weather.weatherapp.data.datasource.remote.AreasRemoteDataSource

class SearchRepository(
        private val remoteDataSource: AreasRemoteDataSource
) {
    fun search(name: String) = remoteDataSource.areas(name)

}