package com.weather.weatherapp.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.weather.weatherapp.R
import com.weather.weatherapp.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.exitProcess


class MainActivity : MvpAppCompatActivity(), MainMvpView, HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @InjectPresenter
    @Inject
    lateinit var mainPresenter: MainPresenter

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var locationListener: LocationListener
    private lateinit var navController: NavController

    companion object {
        private const val REQUEST_CODE_LOCATION = 111
        private const val REQUEST_CODE_LOCATION_SETTINGS_ENABLED = 112
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        locationListener = LocationListener(this, lifecycle){
            mainPresenter.onLocationKnown(lat = it.latitude, lon = it.longitude)
        }
        lifecycle.addObserver(locationListener)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        navController = findNavController(R.id.nav_host_fragment)
        checkPermission()

    }



    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        } else {
            checkLocationEnabled()
        }
    }

    private fun checkLocationEnabled(){
        ContextCompat.getSystemService(this, LocationManager::class.java)?.let {
            if (LocationManagerCompat.isLocationEnabled(it)) {
                locationListener.enable()
            } else {
                val dialog = AlertDialog.Builder(this)
                    .setMessage(R.string.enable_location_settings)
                    .setPositiveButton(R.string.settings) { _, _ ->
                        startActivityForResult(
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                            REQUEST_CODE_LOCATION_SETTINGS_ENABLED
                        )
                    }
                    .setNegativeButton(R.string.quit){ _, _ ->
                        exitProcess(0)
                    }.create()
                dialog.setCanceledOnTouchOutside(false)
                dialog.show()
            }
        }

    }


    private fun initNavController() {
        navController.graph = navController.navInflater.inflate(R.navigation.nav_graph_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> checkLocationEnabled()
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE_LOCATION_SETTINGS_ENABLED){
            checkLocationEnabled()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showHomeScreen() {
        initNavController()
        lifecycle.removeObserver(locationListener)
        activityMainBinding.clMainLayout.visibility = View.VISIBLE
        activityMainBinding.flSplash.visibility = View.GONE
    }


    @ProvidePresenter
    fun providePresenter(): MainPresenter = mainPresenter

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}
