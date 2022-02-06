package com.example.itunesmovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.itunesmovies.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteMovie(movie: Movie)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovie(): Flow<List<Movie>>

    @Query("SELECT trackId FROM favorite_movies")
    fun getAllIdFavoriteMovie(): Flow<List<Int>>

    @Query("DELETE FROM favorite_movies WHERE trackId = :trackId")
    suspend fun removeFavoriteMovie(trackId: Int)

    @Query("SELECT * FROM favorite_movies WHERE trackId = :trackId")
    fun getFavoriteMovie(trackId: Int): Flow<Movie>

    @Query("SELECT * FROM favorite_movies WHERE trackName LIKE :trackName")
    fun searchMovie(trackName: String): Flow<List<Movie>>
}