package com.example.itunesmovies.presentation.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.itunesmovies.models.Movie

@Composable
fun MovieList(
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
                MovieCard(
                    movie = movie,
                    onClick = { Log.i("MYLOGS: " ,"MOVIE CARD CLICKED!")}
                )
            }
        }
    }
}