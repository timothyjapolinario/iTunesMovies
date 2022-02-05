package com.example.itunesmovies.presentation.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.presentation.movielist.MovieListViewModel

@Composable
fun MovieList(
    viewModel: MovieListViewModel,
    modifier: Modifier = Modifier,
    loading: Boolean,
    movies: List<Movie>,
) {
    if(loading){
        CircularProgressIndicator(
            modifier = modifier
        )
    }else{
        LazyColumn(
        ){
            itemsIndexed(
                items = movies
            ){index, movie->
                val isFavorite = mutableStateOf(false)
                isFavorite.value = viewModel.favoriteMovieIds.value.contains(movie.trackId)
                MovieCard(
                    viewModel = viewModel,
                    movie = movie,
                    isFavorite = isFavorite.value,
                    onClick = { Log.i("MYLOGS: " ,"MOVIE CARD CLICKED!")}
                )
            }
        }
    }
}