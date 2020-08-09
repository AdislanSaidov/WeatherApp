package com.weather.weatherapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.weatherapp.data.models.Area
import com.weather.weatherapp.databinding.ViewSearchAreaBinding

class SearchResultAdapter: RecyclerView.Adapter<SearchResultAdapter.AreasViewHolder>() {

    private val areas = mutableListOf<Area>()
    private var listener: (() -> Unit)? = null

    fun setData(areas: List<Area>){
        this.areas.clear()
        this.areas.addAll(areas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreasViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AreasViewHolder(ViewSearchAreaBinding.inflate(inflater))
    }

    override fun getItemCount() = areas.size

    override fun onBindViewHolder(holder: AreasViewHolder, position: Int) {
        holder.bind(areas[position])
    }

    fun setItemClickListener(listener: () -> Unit){
        this.listener = listener
    }

    inner class AreasViewHolder(private val binding: ViewSearchAreaBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(area: Area){
            binding.tvSearchArea.text = area.name
            binding.root.setOnClickListener { listener?.invoke() }
        }
    }
}