package com.example.itunesmovies.data.remote.responses

data class SearchResult(
    val resultCount: Int,
    val results: List<Result>
)