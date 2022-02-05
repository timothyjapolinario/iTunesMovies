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

    val movieTest1 = Movie(
        trackName = "LoL",
        trackId = 1,
        trackPrice = 10.0,
        shortDescription = "cat",
        artworkUrl100 = "https://is5-ssl.mzstatic.com/image/thumb/Video123/v4/33/9d/5d/339d5dd4-2139-eadc-7d5f-89f3b103273d/source/100x100bb.jpg"
    )
    val movieTest2 = Movie(
        trackName = "Wut",
        trackId = 2,
        trackPrice = 10.0,
        artworkUrl100 = "https://is5-ssl.mzstatic.com/image/thumb/Video123/v4/33/9d/5d/339d5dd4-2139-eadc-7d5f-89f3b103273d/source/100x100bb.jpg"
    )
    val movieTest3 = Movie(
        trackName = "Tired",
        trackId = 1,
        trackPrice = 10.0
    )

    val movieListTest = listOf(movieTest1, movieTest2, movieTest3)

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
