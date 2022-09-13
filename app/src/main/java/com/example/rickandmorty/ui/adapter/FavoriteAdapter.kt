package com.example.rickandmorty.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.entity.CharacterData
import com.example.rickandmorty.data.entity.FavoriteCharacterData
import com.example.rickandmorty.databinding.CharacterRecylerviewBinding
import com.example.rickandmorty.ui.fragment.CharactersFragmentDirections
import com.example.rickandmorty.ui.fragment.FavoriteFragmentDirections
import com.example.rickandmorty.util.getImage
import com.google.android.material.snackbar.Snackbar

class FavoriteAdapter(var mContext: Context, var favoriteList: List<FavoriteCharacterData>) : RecyclerView.Adapter<FavoriteAdapter.viewHolder>() {
    inner class viewHolder(binding: CharacterRecylerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: CharacterRecylerviewBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = CharacterRecylerviewBinding.inflate(layoutInflater, parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val favorite = favoriteList[position]
        val t = holder.binding
        t.textViewName.text = favorite.name
        t.textViewGender.text = favorite.gender
        t.imageViewCharacter.getImage(favorite.image)
        t.CardViewCharacter.setOnClickListener {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToCharacterDetailsBottomSheet(character = favorite.toCharacterData())
            findNavController(it).navigate(action)
        }
    }

    fun FavoriteCharacterData.toCharacterData() = CharacterData(
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image,
        episode = listOf("a", "b", "c")
    )

    override fun getItemCount(): Int {
        return favoriteList.size
    }
}