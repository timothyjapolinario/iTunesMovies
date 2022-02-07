package com.example.itunesmovies.repository

import com.example.itunesmovies.data.local.MovieDao
import com.example.itunesmovies.data.local.UserDao
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Single source of data for local
 * */
class LocalMovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    private val userDao: UserDao
) {

    /**
     * Get all id of favorite movies
     * */
    fun getAllFavoriteMovieId(): Flow<List<Int>> =
        movieDao.getAllIdFavoriteMovie()

    /**
     * Get all favorite movie
     * */
    fun getAllFavoriteMovies(): Flow<List<Movie>> =
        movieDao.getAllFavoriteMovie()
    /**
     * Get all searched movie
     * @param trackName
     * */
    fun searchFavoriteMovie(trackName:String): Flow<List<Movie>> =
        movieDao.searchMovie(trackName)

    /**
     * Insert movie to local database
     * @param movie
     * */
    suspend fun insert(movie: Movie) = movieDao.insertFavoriteMovie(movie)

    /**
     * Remove movie from local database
     * @param trackId
     * */
    suspend fun remove(trackId: Int) = movieDao.removeFavoriteMovie(trackId)


    /**
     * Get a single Movie
     * @param trackId
     * */
    fun getMovie(trackId: Int): Flow<Movie> =
        movieDao.getFavoriteMovie(trackId)



}