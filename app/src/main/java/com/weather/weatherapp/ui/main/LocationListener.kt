package com.weather.weatherapp.ui.main

import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.*
import timber.log.Timber

class LocationListener(
    context: Context,
    private val lifecycle: Lifecycle,
    private val callback: (Location) -> Unit
): LifecycleObserver {
    private var locationRequest: LocationRequest = LocationRequest()
    private lateinit var locationCallback: LocationCallback
    private var fusedLocationClient: FusedLocationProviderClient
    private var enabled = false

    init {
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    location?.run {
                        fusedLocationClient.removeLocationUpdates(locationCallback)
                        Timber.e(this.toString())
                        callback.invoke(this)
                    }
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        if (enabled) {
            requestLocation()
        }
    }

    fun enable() {
        enabled = true
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            requestLocation()
        }
    }

    private fun requestLocation() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }
}