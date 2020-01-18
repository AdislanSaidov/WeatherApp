package com.weather.weatherapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.weather.weatherapp.R
import com.weather.weatherapp.databinding.FragmentMainBinding
import com.weather.weatherapp.ui.base.BaseFragment


class MainFragment : BaseFragment(), FragmentManager.OnBackStackChangedListener {

    private lateinit var fragmentMainBinding: FragmentMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMainBinding = FragmentMainBinding.inflate(inflater)

        return fragmentMainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initNavigationController()
    }

    private fun initNavigationController() {

        val appCompatActivity = (activity as AppCompatActivity)
        appCompatActivity.setSupportActionBar(fragmentMainBinding.toolbar)
        val navController = appCompatActivity.findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_gallery),
            fragmentMainBinding.drawerLayout
        )
        appCompatActivity.setupActionBarWithNavController(navController, appBarConfiguration)
        fragmentMainBinding.navView.setupWithNavController(navController)
        val toggle = ActionBarDrawerToggle(
            activity,
            fragmentMainBinding.drawerLayout,
            fragmentMainBinding.toolbar,
            0,
            0
        )
        toggle.syncState()
        fragmentMainBinding.navView.setNavigationItemSelectedListener { menuItem ->
            fragmentMainBinding.drawerLayout.closeDrawers()
            menuItem.isChecked = true
            when (menuItem.itemId) {
            }

            true
        }
    }

//    override fun onBackPressed() {
//        if (fragmentMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            fragmentMainBinding.drawerLayout.closeDrawer(GravityCompat.START)
//        } else {
//            return super.onBackPressed()
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }

    override fun onBackStackChanged() {

    }
}