package com.weather.weatherapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.weatherapp.R
import com.weather.weatherapp.domain.models.UiForecast
import com.weather.weatherapp.ui.home.ForecastAdapter.ForecastViewHolder
import java.util.*

class ForecastAdapter : RecyclerView.Adapter<ForecastViewHolder>() {
    private val promotionList: MutableList<UiForecast> = ArrayList()

    fun setData(promotions: List<UiForecast>) {
        promotionList.clear()
        promotionList.addAll(promotions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_forecast_item, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {}
    override fun getItemCount(): Int {
        return promotionList.size
    }

    inner class ForecastViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view)
}