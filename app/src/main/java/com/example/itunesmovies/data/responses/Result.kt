package com.example.itunesmovies.data.responses

data class Result(
    val resultCount: Int,
    val iTunesMovies: List<iTunesMovie>
)