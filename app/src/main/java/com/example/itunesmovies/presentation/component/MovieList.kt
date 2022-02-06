package com.example.itunesmovies.presentation.component

import android.content.Context
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
import androidx.navigation.NavController
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.presentation.movielist.MovieListViewModel

@Composable
fun MovieList(
    viewModel: MovieListViewModel,
    modifier: Modifier = Modifier,
    loading: Boolean,
    movies: List<Movie>,
    context: Context,
    navController: NavController
) {
    if(loading){
        CircularProgressIndicator(
            modifier = modifier
        )
    }else{
        LazyColumn(){
            itemsIndexed(
                items = movies
            ){index, movie->
                val isFavorite = mutableStateOf(false)
                isFavorite.value = viewModel.favoriteMovieIds.value.contains(movie.trackId)
                MovieCard(
                    viewModel = viewModel,
                    movie = movie,
                    isFavorite = isFavorite.value,
                    onClick = { navController.navigate(
                        if(viewModel.isOnline(context)){
                            "movie_detail_screen/${movie.trackId}"
                        }else{
                            "movie_detail_offline_screen/${movie.trackId}"
                        }
                    )}
                )
            }
        }
    }
}