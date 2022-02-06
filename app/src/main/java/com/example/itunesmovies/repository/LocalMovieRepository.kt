package com.example.itunesmovies.repository

import com.example.itunesmovies.data.local.MovieDao
import com.example.itunesmovies.data.local.UserDao
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalMovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    private val userDao: UserDao
) {
    fun getAllFavoriteMovieId(): Flow<List<Int>> =
        movieDao.getAllIdFavoriteMovie()

    fun getAllFavoriteMovies(): Flow<List<Movie>> =
        movieDao.getAllFavoriteMovie()

    suspend fun insert(movie: Movie) = movieDao.insertFavoriteMovie(movie)

    suspend fun remove(trackId: Int) = movieDao.removeFavoriteMovie(trackId)

    suspend fun insertUser(user: User)= userDao.insertUser(user)


    fun getUser(): Flow<List<User>> =
        userDao.getUser()
}