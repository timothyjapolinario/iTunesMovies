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
}