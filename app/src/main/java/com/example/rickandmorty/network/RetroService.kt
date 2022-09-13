package com.example.rickandmorty.network

import com.example.rickandmorty.data.entity.EpisodeData
import com.example.rickandmorty.data.entity.EpisodeList
import com.example.rickandmorty.data.entity.LocationList
import com.example.rickandmorty.data.entity.RickAndMortyList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroService {
    @GET("character")
    suspend fun getCharacter(@Query("page") query: Int): RickAndMortyList

    @GET("location")
    suspend fun getLocation(@Query("page") query: Int): LocationList

    @GET("episode")
    suspend fun getEpisode(@Query("page") query: Int): EpisodeList

    @GET("character")
    suspend fun getStatusCharacter(@Query("page") query: Int, @Query("status") status:String): RickAndMortyList

    @GET("character")
    suspend fun getGenderCharacter(@Query("page") query: Int, @Query("gender") gender:String): RickAndMortyList

    @GET("episode")
    suspend fun getSeasonEpisode(@Query("page") query: Int , @Query("episode") season:String): EpisodeList

    @GET("episode/{episode}")
    suspend fun getCharacterEpisode(@Path("episode") episodeId: String):List<EpisodeData>
}