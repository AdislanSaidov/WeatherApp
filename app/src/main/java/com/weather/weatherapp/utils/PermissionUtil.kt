package com.weather.weatherapp.utils

import android.content.pm.PackageManager

object PermissionUtil {
    fun isGranted(grantResults: IntArray): Boolean {
        if (grantResults.isEmpty()) {
            return false
        }

        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}