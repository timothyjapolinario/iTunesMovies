package com.example.itunesmovies.models

data class Movie (
    val artistName: String,
    val artworkUrl100: String,
    val artworkUrl30: String,
    val artworkUrl60: String,
    val longDescription: String,
    val primaryGenreName: String,
    val trackHdPrice: Double,
    val trackId: Int,
    val trackName: String,
    val trackPrice: Double,
)