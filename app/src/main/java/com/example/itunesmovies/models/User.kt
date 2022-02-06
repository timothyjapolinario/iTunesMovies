package com.example.itunesmovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User (
    @PrimaryKey
    val id: Int = 0,
    val lastVisitedMovieId: Int? = null
)