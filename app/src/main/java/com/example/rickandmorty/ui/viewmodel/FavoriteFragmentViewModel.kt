package com.example.rickandmorty.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.entity.FavoriteCharacterData
import com.example.rickandmorty.data.repo.FavoriteDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(var frepo: FavoriteDaoRepository) : ViewModel() {
    var favoriteList = MutableLiveData<List<FavoriteCharacterData>>()

    init {
        getFavorite()
        favoriteList = frepo.getFavorite()
    }

    fun getFavorite() {
        frepo.favorite()
    }
}