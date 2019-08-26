package com.example.modelapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baidu.mapapi.search.core.PoiInfo
import com.example.modelapp.R

class LocationAdapter(val data: MutableList<PoiInfo> = mutableListOf())
    : RecyclerView.Adapter<LocationHolder>() {

    private var onItemClickListener = fun(_: Int) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loaction, parent, false)
        return LocationHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setOnItemOnClickListener(function: (Int) -> Unit) {
        onItemClickListener = function
    }
}

class LocationHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val firstTitle: TextView = view.findViewById(R.id.tvTitle)
    private val secendTitle: TextView = view.findViewById(R.id.tvSecendTitle)

    fun bind(data: PoiInfo) {
        firstTitle.text = data.name
        secendTitle.text = data.address
    }

}

