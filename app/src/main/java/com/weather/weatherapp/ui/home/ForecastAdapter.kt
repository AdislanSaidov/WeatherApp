package com.weather.weatherapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.weather.weatherapp.databinding.ViewForecastItemBinding
import com.weather.weatherapp.domain.models.UiForecast
import com.weather.weatherapp.ui.home.ForecastAdapter.ForecastViewHolder
import java.util.*

class ForecastAdapter : RecyclerView.Adapter<ForecastViewHolder>() {
    private val forecasts = ArrayList<UiForecast>()

    fun setData(promotions: List<UiForecast>) {
        forecasts.clear()
        forecasts.addAll(promotions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewForecastItemBinding.inflate(inflater, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    override fun getItemCount() = forecasts.size

    inner class ForecastViewHolder(private val binding: ViewForecastItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(forecast: UiForecast){
            binding.tvTime.text = forecast.dt
            binding.ivWeatherIcon.setImageDrawable(ContextCompat.getDrawable(itemView.context, forecast.icon))
            binding.tvTemp.text = forecast.temp
        }
    }
}