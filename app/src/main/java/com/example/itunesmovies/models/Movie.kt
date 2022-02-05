package com.example.itunesmovies.models

data class Movie (
    val artistName: String? = null,
    val artworkUrl100: String?= null,
    val artworkUrl30: String?= null,
    val artworkUrl60: String?= null,
    val longDescription: String?= null,
    val shortDescription: String?= null,
    val primaryGenreName: String?= null,
    val trackHdPrice: Double?= null,
    val trackId: Int,
    val trackName: String,
    val trackPrice: Double,

)