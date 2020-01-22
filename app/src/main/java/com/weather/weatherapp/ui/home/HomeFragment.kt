package com.weather.weatherapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import com.bumptech.glide.Glide
import com.weather.weatherapp.R
import com.weather.weatherapp.databinding.FragmentHomeBinding
import com.weather.weatherapp.domain.models.UiWeatherData
import com.weather.weatherapp.ui.base.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber
import javax.inject.Inject


class HomeFragment : BaseFragment(), HomeMvpView{

    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    @Inject
    @InjectPresenter
    lateinit var mainPresenter: HomePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme_NoActionBar)

        val localInflater = inflater.cloneInContext(contextThemeWrapper)

        fragmentHomeBinding = FragmentHomeBinding.inflate(localInflater)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }


    override fun showForecastData() {
    }

    override fun showWeatherData(weatherData: UiWeatherData) {
        (activity as AppCompatActivity).supportActionBar?.title = weatherData.name
        fragmentHomeBinding.tvMainTemp.text = weatherData.temp
        fragmentHomeBinding.tvMainShortDesc.text = weatherData.weather.description
        fragmentHomeBinding.tvMainFeelsLikeValue.text = weatherData.feelsLike
        fragmentHomeBinding.tvMainHumidityValue.text = weatherData.humidity
        fragmentHomeBinding.tvMainPressureValue.text = weatherData.pressure
        fragmentHomeBinding.tvMainWindValue.text = weatherData.wind
        fragmentHomeBinding.tvMainPressureValue.text = weatherData.pressure
        fragmentHomeBinding.tvMainVisibilityValue.text = weatherData.visibility
        Glide.with(requireContext()).load(weatherData.weather.icon).into(fragmentHomeBinding.ivMainWeatherIcon)
    }

    @ProvidePresenter
    fun providePresenter(): HomePresenter = mainPresenter

}