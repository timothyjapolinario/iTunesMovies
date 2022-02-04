package com.example.itunesmovies.data.iTunesMovieApi
import com.example.itunesmovies.data.responses.iTunesMovieResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface iTunesMovieApi {

    @GET("search?")
    suspend fun getMovieList(
        @Query("term") term: String,
        @Query("media") media: String
    ): iTunesMovieResult

    @GET("lookup?")
    suspend fun getMovie(
        @Query("id") id: Int
    ): iTunesMovieResult
}