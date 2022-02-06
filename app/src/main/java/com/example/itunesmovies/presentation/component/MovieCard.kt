package com.example.itunesmovies.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.util.CustomImage
import com.example.itunesmovies.R
import com.example.itunesmovies.presentation.movielist.MovieListViewModel
import kotlinx.coroutines.launch

@Composable
fun MovieCard(
    movie:Movie,
    isFavorite:Boolean,
    viewModel:MovieListViewModel,
    onClick: ()-> Unit,
){
    val coroutineScope = rememberCoroutineScope()
    val movieImagePainter = rememberImagePainter(
        data = movie.artworkUrl100,
        builder = {
            //some transformation if needed.
        })
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .fillMaxWidth()
            .heightIn(120.dp)
            .clickable(onClick = onClick),
        elevation = 8.dp
    ){
        Row{
            Column(
                modifier = Modifier
                    .weight(1.8f)
                    .fillMaxWidth()
                    .height(120.dp),
                verticalArrangement = Arrangement.Center
            ){
                CustomImage(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .height(100.dp)
                        .width(100.dp),
                    painter = movieImagePainter,
                    placeHolder = { ShimmerMovieCardItem(imageHeight = 100.dp) },
                    contentDescription = "MovieArtWork")
            }
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 9.dp)

            ){
                Text(text = movie.trackName,
                    fontSize = 15.sp,
                )
                Text(text = movie.primaryGenreName!!,
                    fontSize = 10.sp
                )
                Text(text = movie.trackHdPrice!!.toString(),
                    fontSize = 12.sp
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(top = 9.dp),
            ) {
                Image(
                    painter = painterResource(
                        id = if(isFavorite){
                            R.drawable.heart_red
                        }else{
                            R.drawable.heart_grey
                        }
                    ),
                    contentDescription = "pokemon_logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .clickable { coroutineScope.launch {
                            if(!isFavorite){
                                viewModel.addToFavorites(movie)
                            }else{
                                viewModel.removeFromFavorite(movie)
                            }
                        }
                    }
                )
            }
        }
    }
}
@Preview
@Composable
fun MovieCardPreview(){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .fillMaxWidth()
            .clickable(onClick = { }),
        elevation = 8.dp
    ){
        Row{
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .height(100.dp)

            ){
                ShimmerMovieCardItem(imageHeight = 100.dp)
            }
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .height(100.dp)
            ){
            }
        }
    }
}