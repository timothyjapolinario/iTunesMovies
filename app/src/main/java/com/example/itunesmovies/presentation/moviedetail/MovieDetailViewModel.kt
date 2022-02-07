package com.example.itunesmovies.presentation.moviedetail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.repository.LocalMovieRepository
import com.example.itunesmovies.repository.NetworkMovieRepository
import com.example.itunesmovies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel  @Inject constructor(
    private val repository: NetworkMovieRepository,
    private val localMovieRepository: LocalMovieRepository
): ViewModel(){

    val favoriteMovieIds:MutableState<List<Int>> = mutableStateOf(listOf())
    var isAddingOrRemovingToDatabase = mutableStateOf(false)
    val movies:MutableState<List<Movie>> = mutableStateOf(listOf())
    val currentMovie:MutableState<Movie?> = mutableStateOf(null)

    //Get all favorite movie id
    fun getAllFavoriteId() = viewModelScope.launch(Dispatchers.IO){
        localMovieRepository.getAllFavoriteMovieId().distinctUntilChanged().collect { result->
            try{
                if(result.isNotEmpty()){
                    favoriteMovieIds.value = result
                }else{
                    favoriteMovieIds.value = listOf()
                }
            }catch (e:Exception){
            }
        }
    }

    //Check if there is an internet connection
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    //Get movie from the iTunesApi
    suspend fun getMovieFromNetwork(id:Int): Resource<Movie>{
        return repository.getMovieInfo(id)
    }

    //Get movie from the local database
    fun getMovieFromLocal(id: Int) = viewModelScope.launch(Dispatchers.IO){
        localMovieRepository.getMovie(trackId = id).distinctUntilChanged().collect { result->
            try{
                currentMovie.value = result
            }catch (e:Exception){
            }
        }
    }

    //Remove a favorite movie from the local database
    suspend fun removeFromFavorite(movie: Movie){
        if(!isAddingOrRemovingToDatabase.value){
            Log.i("MYLOGS: ", "removing from database")
            isAddingOrRemovingToDatabase.value = true
            localMovieRepository.remove(movie.trackId)
            isAddingOrRemovingToDatabase.value = false
        }
    }

    //Add a favorite movie to the local database
    suspend fun addToFavorites(movie:Movie){
        if(!isAddingOrRemovingToDatabase.value){
            Log.i("MYLOGS: ", "Adding to database")
            isAddingOrRemovingToDatabase.value = true
            localMovieRepository.insert(movie)
            isAddingOrRemovingToDatabase.value = false
        }
    }
}