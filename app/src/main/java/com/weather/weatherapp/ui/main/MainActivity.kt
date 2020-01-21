package com.weather.weatherapp.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.weather.weatherapp.R
import com.weather.weatherapp.databinding.ActivityMainBinding
import com.weather.weatherapp.databinding.FragmentSplashBinding
import com.weather.weatherapp.utils.PermissionUtil
import moxy.MvpAppCompatActivity
import timber.log.Timber

class MainActivity : MvpAppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var fragmentSplashBinding: FragmentSplashBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    var wayLatitude = 0.0
    var wayLongitude = 0.0
    private val handler = Handler()
    private val runnable = {

    }

    companion object {
        private const val REQUEST_CODE_LOCATION = 111
    }

    private fun initLocation() {
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
                    if (location != null) {
                        wayLatitude = location.latitude
                        wayLongitude = location.longitude
                        Timber.e("lat: $wayLatitude  lon: $wayLongitude")
                        fusedLocationClient.removeLocationUpdates(locationCallback)
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initNavigationController()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(fragmentSplashBinding.btn,"shouldShowRequestPermissionRationale", Snackbar.LENGTH_SHORT).show()
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_LOCATION
                )
                initLocation()
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_LOCATION
                )
                initLocation()

            }
        } else {
            initLocation()
            Timber.e("aaaaaaa")
            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location -> Timber.e("lat: ${location?.latitude}  lon: ${location?.longitude}")};
            val locationRequest = LocationRequest.create()
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

        }
    }


    private fun initNavigationController() {

        setSupportActionBar(activityMainBinding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_gallery),
            activityMainBinding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        activityMainBinding.navView.setupWithNavController(navController)
        val toggle = ActionBarDrawerToggle(
            this,
            activityMainBinding.drawerLayout,
            activityMainBinding.toolbar,
            0,
            0
        )
        toggle.syncState()
        activityMainBinding.navView.setNavigationItemSelectedListener { menuItem ->
            activityMainBinding.drawerLayout.closeDrawers()
            menuItem.isChecked = true
            when (menuItem.itemId) {
            }

            true
        }
    }

    override fun onBackPressed() {
        if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            return super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Timber.e("ccccccc")
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                Timber.e("aaaaaaaaa")
                if (PermissionUtil.isGranted(grantResults)) {
                    Snackbar.make(fragmentSplashBinding.btn, "", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(fragmentSplashBinding.btn, "not granted", Snackbar.LENGTH_SHORT).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}
