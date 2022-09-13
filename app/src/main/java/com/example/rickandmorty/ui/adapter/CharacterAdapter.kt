package com.example.rickandmorty.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.entity.CharacterData
import com.example.rickandmorty.ui.fragment.CharactersFragmentDirections
import com.example.rickandmorty.util.getImage

class CharacterAdapter(private val callback:(CharacterData)->Unit) : PagingDataAdapter<CharacterData, CharacterAdapter.viewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.character_recylerview, parent, false)
        return viewHolder(inflater,callback)
    }

    class viewHolder(view: View,private val callback:(CharacterData)->Unit) : RecyclerView.ViewHolder(view) {
        val imageViewCharacter: ImageView = view.findViewById(R.id.imageViewCharacter)
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewGender: TextView = view.findViewById(R.id.textViewGender)
        val cardView: CardView = view.findViewById(R.id.CardViewCharacter)
        fun bind(data: CharacterData) {
            textViewName.text = data.name
            textViewGender.text = data.gender
            data.image?.let { imageViewCharacter.getImage(it) }
            cardView.setOnClickListener {
                callback.invoke(data)
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<CharacterData>() {
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.name == newItem.name && oldItem.gender == newItem.gender
        }
    }
}