package com.example.rickandmorty.network

import com.example.rickandmorty.data.entity.RickAndMortyList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {
    @GET("character")
    suspend fun getDataFromAPI(@Query("page") query: Int):RickAndMortyList
}