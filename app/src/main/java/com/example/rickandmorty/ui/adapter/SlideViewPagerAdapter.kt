package com.example.rickandmorty.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R

class SlideViewPagerAdapter (val images: List<Int>) : RecyclerView.Adapter<SlideViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgView: ImageView = itemView.findViewById(R.id.ivImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curImage = images[position]
        holder.imgView.setImageResource(curImage)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}