package com.example.itunesmovies.presentation.moviedetail

import android.util.Log
import androidx.compose.foundation.*

import androidx.compose.foundation.layout.*

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.itunesmovies.presentation.component.ShimmerMovieCardItem
import com.example.itunesmovies.util.CustomImage
import com.example.itunesmovies.util.Resource
import kotlinx.coroutines.launch

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    trackId: Int,
    navController: NavController
) {
    val movie = produceState<Resource<Movie>>(initialValue = Resource.Loading()){
        value = viewModel.getMovieFromNetwork(trackId)
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
                LongDescriptionSection(movieInfo = movieInfo.data!!)
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

@Composable
fun MovieDetailSection(
    movieInfo: Movie,
    viewModel: MovieDetailViewModel = hiltViewModel()
){
    val coroutineScope = rememberCoroutineScope()
    val movieImagePainter = rememberImagePainter(
        data = movieInfo.artworkUrl100,
        builder = {
            //some transformation if needed.
        })

    val isFavorite = remember{mutableStateOf(false)}
    isFavorite.value = viewModel.favoriteMovieIds.value.contains(movieInfo.trackId)
    viewModel.getAllFavoriteId()
    Box(){
        Column(modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 20.dp)
        ) {
            Text(text = movieInfo.trackName,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 24.sp,
                textAlign = TextAlign.Center)
            CustomImage(painter = movieImagePainter, 
                placeHolder = { ShimmerMovieCardItem(imageHeight = 200.dp)}, 
                contentDescription = "movie_artwork",
                modifier = Modifier
                    .height(500.dp)
                    .width(300.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ){
                Text(
                    text = "$" + movieInfo.trackPrice.toString(),
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Image(
                    painter = painterResource(
                        id = if(isFavorite.value){
                            R.drawable.heart_red
                        }else{
                            R.drawable.heart_grey
                        }
                    ),
                    contentDescription = "heart",
                    modifier = Modifier
                        .height(38.dp)
                        .clickable {
                            coroutineScope.launch {
                                if (!isFavorite.value) {
                                    viewModel.addToFavorites(movieInfo)
                                } else {
                                    viewModel.removeFromFavorite(movieInfo)
                                }
                                //viewModel.getAllFavoriteId()
                            }
                        }
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}


@Composable
fun LongDescriptionSection(
    movieInfo: Movie
){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(start=7.dp, end = 7.dp)
    ){
        LongDescriptionPart(title = "Genre", content = movieInfo.primaryGenreName!!)
        Spacer(modifier = Modifier.height(5.dp))
        LongDescriptionPart(title = "Description", content = movieInfo.longDescription!!)
    }
}

@Composable
fun LongDescriptionPart(
    title: String,
    content: String,
){
    Column{
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = content,
            fontSize = 18.sp
        )
    }
}

