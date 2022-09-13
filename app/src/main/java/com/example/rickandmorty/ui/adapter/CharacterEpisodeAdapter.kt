package com.example.rickandmorty.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.entity.EpisodeData
import com.example.rickandmorty.databinding.EpisodeRecyclerviewBinding


class CharacterEpisodeAdapter(var mContext: Context, var episodeList: List<EpisodeData>) : RecyclerView.Adapter<CharacterEpisodeAdapter.viewHolder>() {
    inner class viewHolder(binding: EpisodeRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: EpisodeRecyclerviewBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterEpisodeAdapter.viewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = EpisodeRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val episode = episodeList[position]
        val t = holder.binding
        t.textViewName.text = episode.name
        t.textViewEpisode.text = episode.episode
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }
}