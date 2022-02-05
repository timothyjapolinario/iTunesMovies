package com.example.itunesmovies.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.itunesmovies.models.Movie

@Composable
fun MovieList(
    loading: Boolean,
    movies: List<Movie>,
) {
    Box(modifier = Modifier.fillMaxSize()){
        if(loading){

        }
    }
}