package com.example.rickandmorty.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.entity.LocationData

class LocationAdapter() : PagingDataAdapter<LocationData, LocationAdapter.viewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.location_recyclerview, parent, false)
        return viewHolder(inflater)
    }

    class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView:ImageView = view.findViewById(R.id.imageViewLocation)
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewType: TextView = view.findViewById(R.id.textViewType)
        fun bind(data: LocationData){
            textViewName.text = data.name
            textViewType.text = data.type
            imageView.setImageResource(R.drawable.location)
        }
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<LocationData>(){
        override fun areItemsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
            return oldItem.name == newItem.name && oldItem.type == newItem.type
        }
    }
}