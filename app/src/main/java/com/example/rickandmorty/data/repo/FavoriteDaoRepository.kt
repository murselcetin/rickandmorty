package com.example.rickandmorty.data.repo

import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.data.entity.CharacterData
import com.example.rickandmorty.data.entity.FavoriteCharacterData
import com.example.rickandmorty.room.FavoriteDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteDaoRepository @Inject constructor(var fdao: FavoriteDAO) {
    var favoriteList: MutableLiveData<List<FavoriteCharacterData>>

    init {
        favoriteList = MutableLiveData()
    }

    fun getFavorite(): MutableLiveData<List<FavoriteCharacterData>> {
        return favoriteList
    }

    fun addFavorite(favoriteCharacter: FavoriteCharacterData) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            fdao.addFavorite(favoriteCharacter)
        }
    }

    fun deleteFavorite(favoriteId:Int) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val deleteCharacter = FavoriteCharacterData(favoriteId, "","","","","","")
            fdao.deleteFavorite(deleteCharacter)
            favorite()
        }
    }

    suspend fun favoriteControl(characterName: String): List<FavoriteCharacterData> {
        return fdao.favoriteControl(characterName)
    }

    suspend fun favoriteId(favoriteCharacterName: String): FavoriteCharacterData {
        return fdao.favoriteId(favoriteCharacterName)
    }

    fun favorite() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            favoriteList.value = fdao.allFavorite()
        }
    }

}