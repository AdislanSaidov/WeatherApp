package com.weather.weatherapp.ui.main

import android.os.Bundle
import com.weather.weatherapp.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }

}
