package com.example.itunesmovies.presentation.movielist

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.presentation.component.SearchBar
import com.example.itunesmovies.util.Resource

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel()
){
    val movieInfo = produceState<Resource<List<Movie>>>(initialValue = Resource.Loading()){
        value = viewModel.searchMovie()
    }.value

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ){
        Box {
            SearchBar(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                hint = "Search"
            ){
                //Onsearch
            }
            MovieInfoStateWrapper(movieInfo = movieInfo)
        }
    }
}

@Composable
fun MovieInfoStateWrapper(
    movieInfo: Resource<List<Movie>>
){
    when(movieInfo){
        is Resource.Success->{
            Log.i("MYLOGS: ", movieInfo.data!![0].artistName)
        }
        is Resource.Error->{
            Log.i("MYLOGS: ", "error")
        }
        is Resource.Loading->{
            Log.i("MYLOGS: ", "loading")
        }
    }
}