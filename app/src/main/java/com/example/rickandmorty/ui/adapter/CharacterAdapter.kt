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
import com.example.rickandmorty.data.entity.CharacterData
import com.example.rickandmorty.util.getImage
import com.squareup.picasso.Picasso

class CharacterAdapter(): PagingDataAdapter<CharacterData, CharacterAdapter.viewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: CharacterAdapter.viewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapter.viewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.character_recylerview, parent, false)
        return viewHolder(inflater)
    }

    class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewCharacter: ImageView = view.findViewById(R.id.imageViewCharacter)
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewGender: TextView = view.findViewById(R.id.textViewGender)
        fun bind(data: CharacterData){
            textViewName.text = data.name
            textViewGender.text = data.gender
            data.image?.let { imageViewCharacter.getImage(it) }
        }
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<CharacterData>(){
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.name == newItem.name && oldItem.gender == newItem.gender
        }

    }
}