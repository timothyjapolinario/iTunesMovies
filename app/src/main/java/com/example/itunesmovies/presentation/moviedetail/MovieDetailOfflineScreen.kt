package com.example.itunesmovies.presentation.moviedetail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.presentation.component.LongDescriptionSection
import com.example.itunesmovies.presentation.component.MovieDetailSection
import kotlinx.coroutines.coroutineScope

@Composable
fun MovieDetailOfflineScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    trackId: Int
){
    viewModel.getMovieFromLocal(trackId)
    val movie = viewModel.currentMovie.value
    Column(){
        if(movie != null){
            MovieDetailSection(movieInfo = movie)
            LongDescriptionSection(movieInfo = movie)
        }
    }
}