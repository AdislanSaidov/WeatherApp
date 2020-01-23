package com.weather.weatherapp.ui.main

import com.weather.weatherapp.data.models.Coord

interface OnLocationListener {
    fun onLocationKnown(coord: Coord)
}