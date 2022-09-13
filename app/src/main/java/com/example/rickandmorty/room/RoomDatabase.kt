package com.example.rickandmorty.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmorty.data.entity.CharacterData
import com.example.rickandmorty.data.entity.FavoriteCharacterData

@Database(entities = [FavoriteCharacterData::class], version = 1)
abstract class RoomDatabase : RoomDatabase(){
    abstract fun getFavorite() : FavoriteDAO
}