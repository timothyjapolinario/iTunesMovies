package com.example.itunesmovies.presentation

import android.graphics.Movie
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.itunesmovies.presentation.moviedetail.MovieDetailScreen
import com.example.itunesmovies.presentation.movielist.MovieListScreen
import com.example.itunesmovies.presentation.theme.ITunesMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ITunesMoviesTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "movie_list_screen"){
                    composable("movie_list_screen"){
                        MovieListScreen(navController = navController)
                    }
                    composable("movie_detail_screen/{trackId}",
                        arguments = listOf(
                            navArgument("trackId"){
                                type = NavType.IntType
                            }
                        )
                    ){
                        val trackId = remember{
                            it.arguments?.getInt("trackId")
                        }
                        MovieDetailScreen(trackId = trackId!!, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(
) {
}