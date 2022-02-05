package com.example.itunesmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.itunesmovies.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class FavoriteMoviesDatabase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}