package com.weather.weatherapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.weather.weatherapp.R
import com.weather.weatherapp.databinding.FragmentHomeBinding
import com.weather.weatherapp.domain.models.UiForecast
import com.weather.weatherapp.domain.models.UiWeatherData
import com.weather.weatherapp.ui.base.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class HomeFragment : BaseFragment(), HomeMvpView{

    private lateinit var binding: FragmentHomeBinding

    @Inject
    @InjectPresenter
    lateinit var mainPresenter: HomePresenter
    private val forecastAdapter = ForecastAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme_NoActionBar)

        val localInflater = inflater.cloneInContext(contextThemeWrapper)

        binding = FragmentHomeBinding.inflate(localInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initForecasts()
    }

    private fun initForecasts() {
        binding.rvMainForecast.adapter = forecastAdapter
        binding.rvMainForecast.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }


    override fun showForecastData(forecasts: List<UiForecast>) {
        forecastAdapter.setData(forecasts)
    }

    override fun showWeatherData(weatherData: UiWeatherData) {
        (activity as AppCompatActivity).supportActionBar?.title = weatherData.name
        binding.tvMainTemp.text = weatherData.temp
        binding.tvMainShortDesc.text = weatherData.weather.description
        binding.tvMainFeelsLikeValue.text = weatherData.feelsLike
        binding.tvMainHumidityValue.text = weatherData.humidity
        binding.tvMainPressureValue.text = weatherData.pressure
        binding.tvMainWindValue.text = weatherData.wind
        binding.tvMainPressureValue.text = weatherData.pressure
        binding.tvMainVisibilityValue.text = weatherData.visibility
        Glide.with(requireContext()).load(weatherData.weather.icon).into(binding.ivMainWeatherIcon)
    }

    @ProvidePresenter
    fun providePresenter(): HomePresenter = mainPresenter

}