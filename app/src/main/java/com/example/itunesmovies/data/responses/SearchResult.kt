package com.example.itunesmovies.data.responses

data class SearchResult(
    val resultCount: Int,
    val results: List<Result>
)