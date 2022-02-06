package com.example.itunesmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.models.User

@Database(entities = [Movie::class, User::class], version = 3, exportSchema = false)
abstract class FavoriteMoviesDatabase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getUserDao(): UserDao
}