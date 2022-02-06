package com.example.itunesmovies.presentation.moviedetail

import android.util.Log
import androidx.compose.foundation.*

import androidx.compose.foundation.layout.*

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.itunesmovies.R
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.presentation.component.LongDescriptionSection
import com.example.itunesmovies.presentation.component.MovieDetailSection
import com.example.itunesmovies.presentation.component.ShimmerMovieCardItem
import com.example.itunesmovies.util.CustomImage
import com.example.itunesmovies.util.Resource
import kotlinx.coroutines.launch

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    trackId: Int,

) {
    val context = LocalContext.current
    val movie = produceState<Resource<Movie>>(initialValue = Resource.Loading()){
        if(viewModel.isOnline(context)){
            value = viewModel.getMovieFromNetwork(trackId)
        }

    }.value
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        MovieDetailStateWrapper(movieInfo = movie)
    }
}

@Composable
fun MovieDetailStateWrapper(
    movieInfo: Resource<Movie>
){
    when(movieInfo){
        is Resource.Success->{
            Column(){
                MovieDetailSection(movieInfo = movieInfo.data!!)
                LongDescriptionSection(movieInfo = movieInfo.data)
            }
        }
        is Resource.Error->{
            Log.i("MYLOGS: ", "Error")
        }
        is Resource.Loading->{
            Log.i("MYLOGS: ", "Loading")
        }
    }
}








