package com.example.rickandmorty.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.entity.EpisodeData
import com.example.rickandmorty.data.entity.FavoriteCharacterData
import com.example.rickandmorty.data.repo.FavoriteDaoRepository
import com.example.rickandmorty.network.RetroInstance
import com.example.rickandmorty.network.RetroService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsBottomSheetViewModel @Inject constructor(var frepo: FavoriteDaoRepository,var retroService: RetroService) :
    ViewModel() {

    val favoriteControl = MutableLiveData<Boolean>()
    val favoriteId = MutableLiveData<Int>()
    val episodeList = MutableLiveData<List<EpisodeData>>()

    fun favoriteAdd(characterData: FavoriteCharacterData) {
        frepo.addFavorite(characterData)
    }

    fun favoriteDelete(favoriteId: Int) {
        frepo.deleteFavorite(favoriteId)
    }

    fun favoriteId(favoriteCharacterName: String){
        viewModelScope.launch {
            favoriteId.value = frepo.favoriteId(favoriteCharacterName).id
        }
    }

    fun favoriteControl(characterName: String) {
        viewModelScope.launch {
            favoriteControl.value = frepo.favoriteControl(characterName).size > 0
        }
    }

    suspend fun characterEpisode(episodeId:String){
        episodeList.value = retroService.getCharacterEpisode(episodeId)
    }
}