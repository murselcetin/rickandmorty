package com.example.rickandmorty.data.entity

data class LocationList(val info: Info, val results: List<LocationData>)

data class LocationData(
    val name: String?,
    val type: String?,
    val dimension: String?,
    val residents: List<String>)
