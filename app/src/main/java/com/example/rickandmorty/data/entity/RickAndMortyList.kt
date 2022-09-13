package com.example.rickandmorty.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable


data class RickAndMortyList(val info: Info, val results: List<CharacterData>)

data class CharacterData(val name: String?,val status: String?, val species: String?, val gender: String?, val image: String?,val episode:List<String>): Serializable

data class Info(val count: Int?, val pages: String?, val next: String?, val prev: String?)

