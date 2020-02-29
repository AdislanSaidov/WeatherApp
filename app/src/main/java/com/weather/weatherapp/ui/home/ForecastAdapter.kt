package com.weather.weatherapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.weather.weatherapp.R
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
        val view = inflater.inflate(R.layout.view_forecast_item, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }

    inner class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private var tvTime: TextView = view.findViewById(R.id.tv_time)
        private var ivWeather: ImageView = view.findViewById(R.id.iv_weather_icon)
        private var tvTemp: TextView = view.findViewById(R.id.tv_temp)

        fun bind(forecast: UiForecast){
            tvTime.text = forecast.dt
            ivWeather.setImageDrawable(ContextCompat.getDrawable(itemView.context, forecast.icon))
            tvTemp.text = forecast.temp
        }
    }
}