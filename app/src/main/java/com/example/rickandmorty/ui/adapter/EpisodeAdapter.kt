package com.example.rickandmorty.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.entity.EpisodeData


class EpisodeAdapter: PagingDataAdapter<EpisodeData, EpisodeAdapter.viewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.episode_recyclerview, parent, false)
        return viewHolder(inflater)
    }

    class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewEpisode: TextView = view.findViewById(R.id.textViewEpisode)
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        fun bind(data: EpisodeData){
            textViewName.text = data.name
            textViewEpisode.text = data.episode
        }
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<EpisodeData>(){

        override fun areItemsTheSame(oldItem: EpisodeData, newItem: EpisodeData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: EpisodeData, newItem: EpisodeData): Boolean {
            return oldItem.name == newItem.name && oldItem.episode == newItem.episode
        }
    }
}