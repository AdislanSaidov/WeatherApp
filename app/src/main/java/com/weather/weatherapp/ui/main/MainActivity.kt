package com.weather.weatherapp.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.gms.location.LocationRequest
import com.weather.weatherapp.R
import com.weather.weatherapp.databinding.ActivityMainBinding
import com.weather.weatherapp.utils.PermissionUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainMvpView, HasAndroidInjector {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @InjectPresenter
    @Inject
    lateinit var mainPresenter: MainPresenter

    companion object {
        private const val REQUEST_CODE_LOCATION = 111
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val metrics = resources.displayMetrics
        val densityDpi = (metrics.density * 160f).toInt()
        Timber.e("dens: $densityDpi")
        checkPermission()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                Snackbar.make(fragmentSplashBinding.btn,"shouldShowRequestPermissionRationale", Snackbar.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
            }
        } else {
//            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location -> Timber.e("lat: ${location?.latitude}  lon: ${location?.longitude}")};
            val locationRequest = LocationRequest.create()

        }
    }


    private fun initNavigationController() {
//        setSupportActionBar(activityMainBinding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        navController.graph = navController.navInflater.inflate(R.navigation.nav_graph_main)
//        appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.nav_home, R.id.nav_gallery)
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        activityMainBinding.toolbar.setNavigationIcon(R.drawable.ic_noun_menu_1166840)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                mainPresenter.onLocationPermissionGranted()
                if (PermissionUtil.isGranted(grantResults)) {
//                    Snackbar.make(fragmentSplashBinding.btn, "", Snackbar.LENGTH_SHORT).show();
                } else {
//                    Snackbar.make(fragmentSplashBinding.btn, "not granted", Snackbar.LENGTH_SHORT).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @ProvidePresenter
    fun providePresenter(): MainPresenter = mainPresenter

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun showHomeScreen() {
        initNavigationController()
        activityMainBinding.clMainLayout.visibility = View.VISIBLE
        activityMainBinding.flSplash.visibility = View.GONE
    }

}
