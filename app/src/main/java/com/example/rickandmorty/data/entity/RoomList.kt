package com.example.rickandmorty.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "favorite")
data class FavoriteCharacterData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") @NotNull val id:Int,
    @ColumnInfo(name = "name") @NotNull val name: String,
    @ColumnInfo(name = "status") @NotNull val status: String,
    @ColumnInfo(name = "species") @NotNull val species: String,
    @ColumnInfo(name = "gender") @NotNull val gender: String,
    @ColumnInfo(name = "image") @NotNull val image: String,
    @ColumnInfo(name = "episode") @NotNull val episode: String): Serializable