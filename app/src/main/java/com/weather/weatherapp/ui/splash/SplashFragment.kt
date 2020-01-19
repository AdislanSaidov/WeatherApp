package com.weather.weatherapp.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.weather.weatherapp.R
import com.weather.weatherapp.databinding.FragmentSplashBinding
import com.weather.weatherapp.ui.base.BaseFragment
import com.weather.weatherapp.utils.PermissionUtil
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber
import javax.inject.Inject


class SplashFragment : BaseFragment(), SplashMvpView, SplashNavigator {

    @Inject
    @InjectPresenter
    lateinit var splashPresenter: SplashPresenter
    private lateinit var fragmentSplashBinding: FragmentSplashBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    var wayLatitude = 0.0
    var wayLongitude = 0.0

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

                        fusedLocationClient.removeLocationUpdates(locationCallback)
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        splashPresenter.setNavigator(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentSplashBinding = FragmentSplashBinding.inflate(inflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        fragmentSplashBinding.btn.setOnClickListener {
            splashPresenter.onLocationKnown()
        }

        return fragmentSplashBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkPermission()

    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(fragmentSplashBinding.btn,"shouldShowRequestPermissionRationale", Snackbar.LENGTH_SHORT).show()
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
                initLocation()
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
                initLocation()
            }
        } else {
            initLocation()
            splashPresenter.onLocationKnown()
        }
    }


    @ProvidePresenter
    fun providePresenter(): SplashPresenter = splashPresenter


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Timber.e("ccccccc")
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                Timber.e("aaaaaaaaa")
                if (PermissionUtil.isGranted(grantResults)) {
                    Snackbar.make(fragmentSplashBinding.btn, "", Snackbar.LENGTH_SHORT).show();
                    splashPresenter.onLocationKnown()
                } else {
                    Snackbar.make(fragmentSplashBinding.btn, "not granted", Snackbar.LENGTH_SHORT).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun navigateToMain() {
        Handler().postDelayed({
            val a = activity as AppCompatActivity
            a.findNavController(R.id.nav_host_fragment).navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
        }, 1500)

    }

}