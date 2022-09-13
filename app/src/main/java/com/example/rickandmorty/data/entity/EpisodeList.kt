package com.example.rickandmorty.data.entity

data class EpisodeList(val info: Info, val results: List<EpisodeData>)

data class EpisodeData(val name: String?, val air_date: String?, val episode: String?, val characters: List<String>)
