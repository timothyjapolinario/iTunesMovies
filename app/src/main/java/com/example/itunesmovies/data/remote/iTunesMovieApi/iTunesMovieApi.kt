package com.example.itunesmovies.data.remote.iTunesMovieApi
import com.example.itunesmovies.data.remote.responses.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface iTunesMovieApi {

    @GET("search?")
    suspend fun getMovieList(
        @Query("term") term: String,
        @Query("media") media: String
    ): SearchResult

    @GET("lookup?")
    suspend fun getMovie(
        @Query("id") id: Int
    ): SearchResult
}