package com.example.itunesmovies.presentation.moviedetail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
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

    @OptIn(ExperimentalMaterialApi::class)
    val bottomDrawerState = BottomDrawerState(BottomDrawerValue.Closed)
    val favoriteMovieIds:MutableState<List<Int>> = mutableStateOf(listOf())

    var isAddingOrRemovingToDatabase = mutableStateOf(false)
    val movies:MutableState<List<Movie>> = mutableStateOf(listOf())
    val searchQuery = mutableStateOf("star")
    val currentMovie:MutableState<Movie?> = mutableStateOf(null)

    fun getAllFavoriteId() = viewModelScope.launch(Dispatchers.IO){
        localMovieRepository.getAllFavoriteMovieId().distinctUntilChanged().collect { result->
            try{
                if(result.isNotEmpty()){
                    favoriteMovieIds.value = result
                    Log.i("MYLOGS: ID-", favoriteMovieIds.value[0].toString())
                }
            }catch (e:Exception){
            }
        }
    }

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

    suspend fun searchMovie(): Resource<List<Movie>> {
        return repository.getSearchMovieList("cat")
    }

    suspend fun getMovieFromNetwork(id:Int): Resource<Movie>{
        return repository.getMovieInfo(id)
    }
    fun getMovieFromLocal(id: Int) = viewModelScope.launch(Dispatchers.IO){
        localMovieRepository.getMovie(trackId = id).distinctUntilChanged().collect { result->
            try{
                currentMovie.value = result
            }catch (e:Exception){
            }
        }
    }

    suspend fun removeFromFavorite(movie: Movie){
        if(!isAddingOrRemovingToDatabase.value){
            Log.i("MYLOGS: ", "Adding to database")
            isAddingOrRemovingToDatabase.value = true
            localMovieRepository.remove(movie.trackId)
            isAddingOrRemovingToDatabase.value = false
        }
    }
    suspend fun addToFavorites(movie:Movie){
        if(!isAddingOrRemovingToDatabase.value){
            Log.i("MYLOGS: ", "Adding to database")
            isAddingOrRemovingToDatabase.value = true
            localMovieRepository.insert(movie)
            isAddingOrRemovingToDatabase.value = false
        }
    }
}