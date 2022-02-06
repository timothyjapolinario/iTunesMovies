package com.example.itunesmovies.presentation.movielist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.presentation.component.SearchBar
import com.example.itunesmovies.util.Resource
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.itunesmovies.presentation.component.MovieList
import kotlinx.coroutines.launch
import com.example.itunesmovies.R
@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel(),
    navController: NavController
){
    Log.i("MYLOGS", "RECOMPOSED!!!")

    viewModel.getAllFavoriteId()

    val movies = viewModel.movies.value
    val coroutineScope = rememberCoroutineScope()
    val isLoading = viewModel.isLoading.value

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ){
        Column {
            val context = LocalContext.current
            Row(modifier = Modifier.fillMaxWidth()){
                SearchBar(modifier = Modifier
                    .padding(16.dp)
                    .weight(3f)
                    .fillMaxWidth(.80f),
                    hint = "Search",
                    newSearch = {coroutineScope.launch {
                        //Online but not on favorites
                        if(viewModel.isOnline(context) && !viewModel.isOnFavorite.value){
                            viewModel.newSearch()
                        }//
                        else if(viewModel.isOnFavorite.value){
                            viewModel.getAllFavoriteMovies()
                            viewModel.searchMovieLocal()
                            Log.i("TRACE", "line 80")
                        }
                    }}
                )
                Image(painter = painterResource(
                    if(viewModel.isOnFavorite.value){
                        R.drawable.heart_red
                    }else{
                        R.drawable.heart_grey
                    }
                ),
                    contentDescription = "logo",
                    modifier = Modifier
                        .weight(2f)
                        .height(35.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {coroutineScope.launch {
                                viewModel.onClickFavorite()
                            }
                        }
                )
            }

            if(!viewModel.isOnline(context) && !viewModel.isOnFavorite.value){
                Column(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                ){
                    Image(painter = painterResource(
                        id =R.drawable.no_wifi_grey_logo ),
                        contentDescription ="no_wifi logo",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(text = "No Internet Connection",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }else if(viewModel.isOnline(context) ||viewModel.isOnFavorite.value ) {
                MovieList(
                    viewModel = viewModel,
                    modifier = Modifier
                        .align(CenterHorizontally),
                    loading = isLoading,
                    movies = movies,
                    context = context,
                    navController = navController
                )
                Log.i("MYLOGS: list size- ",movies.size.toString())
            }
        }
    }
}
