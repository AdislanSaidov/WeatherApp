package com.weather.weatherapp.data.datasource

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.weather.weatherapp.data.models.Coord
import com.weather.weatherapp.ui.main.OnLocationListener
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LocationProvider constructor(val fusedLocationClient: FusedLocationProviderClient) {

    private lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    var coord: Coord? = null
    private var onLocationListener: OnLocationListener? = null



    @SuppressLint("MissingPermission")
    fun requestLocation(){
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    location?.run {
                        fusedLocationClient.removeLocationUpdates(locationCallback)
                        coord = Coord(lat = latitude, lon = longitude)
                        onLocationListener?.onLocationKnown(coord!!)
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

    }

    fun setOnLocationListener(onLocationListener: OnLocationListener){
        this.onLocationListener = onLocationListener
    }

}