package com.example.itunesmovies.repository

import com.example.itunesmovies.data.local.MovieDao
import com.example.itunesmovies.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalMovieRepository @Inject constructor(
    private val movieDao: MovieDao
) {
    fun getAllFavoriteMovieId(): Flow<List<Int>> =
        movieDao.getAllIdFavoriteMovie()

    fun getAllFavoriteMovies(): Flow<List<Movie>> =
        movieDao.getAllFavoriteMovie()
    suspend fun insert(movie: Movie) = movieDao.insertFavoriteMovie(movie)
}