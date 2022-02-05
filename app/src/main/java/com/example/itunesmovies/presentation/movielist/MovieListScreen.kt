package com.example.itunesmovies.presentation.movielist

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.presentation.component.SearchBar
import com.example.itunesmovies.util.Resource
import androidx.lifecycle.viewModelScope
import com.example.itunesmovies.presentation.component.MovieList
import kotlinx.coroutines.launch

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel()
){
    val movies = viewModel.movies.value
    val coroutineScope = rememberCoroutineScope()
    val isLoading = viewModel.isLoading.value
    val movieInfo = produceState<Resource<List<Movie>>>(initialValue = Resource.Loading()){
        value = viewModel.searchMovie()
    }.value

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ){
        Column {
            SearchBar(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                hint = "Search",
                newSearch = {coroutineScope.launch {
                    viewModel.newSearch()
                }}
            )
            MovieList(
                modifier = Modifier
                    .align(CenterHorizontally),
                loading = isLoading,
                movies = movies
            )
        }
    }
}
