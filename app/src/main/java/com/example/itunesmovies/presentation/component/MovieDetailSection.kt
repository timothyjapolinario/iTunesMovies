package com.example.itunesmovies.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.itunesmovies.R
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.presentation.moviedetail.MovieDetailViewModel
import com.example.itunesmovies.util.CustomImage
import kotlinx.coroutines.launch

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

    val isFavorite = remember{ mutableStateOf(false) }
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
                            }
                        }
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}