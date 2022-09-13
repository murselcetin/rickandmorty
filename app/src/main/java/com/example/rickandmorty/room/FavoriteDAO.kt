package com.example.rickandmorty.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rickandmorty.data.entity.CharacterData
import com.example.rickandmorty.data.entity.FavoriteCharacterData

@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM favorite")
    suspend fun allFavorite(): List<FavoriteCharacterData>

    @Insert
    suspend fun addFavorite(favoriteCharacter: FavoriteCharacterData)

    @Delete
    suspend fun deleteFavorite(favoriteCharacter: FavoriteCharacterData)

    @Query("SELECT * FROM favorite WHERE name = :characterName LIMIT 1")
    suspend fun favoriteControl(characterName: String): List<FavoriteCharacterData>

    @Query("SELECT * FROM favorite WHERE name = :favoriteName LIMIT 1")
    suspend fun favoriteId(favoriteName: String): FavoriteCharacterData
}