package com.example.itunesmovies.presentation.movielist

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
class MovieListViewModel  @Inject constructor(
    private val repository: NetworkMovieRepository,
    private val localMovieRepository: LocalMovieRepository
): ViewModel(){
    val isOnFavorite = mutableStateOf(false)
    val favoriteMovieIds:MutableState<List<Int>> = mutableStateOf(listOf())
    val isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")
    var isAddingOrRemovingToDatabase = mutableStateOf(false)
    val movies:MutableState<List<Movie>> = mutableStateOf(listOf())
    val searchQuery = mutableStateOf("")
    val cachedMovies:MutableState<List<Movie>> = mutableStateOf(listOf())
    val favoriteMovies:MutableState<List<Movie>> = mutableStateOf(listOf())

    //Get all favorite movie id
    fun getAllFavoriteId() = viewModelScope.launch(Dispatchers.IO){
        localMovieRepository.getAllFavoriteMovieId().distinctUntilChanged().collect { result->
            try{
                if(result.isNotEmpty()){
                    favoriteMovieIds.value = result
                }
            }catch (e:Exception){
            }
        }
    }

    //Check if there is internet connection
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

    //Method is called when the "favorite button" is clicked
    fun onClickFavorite(){
        isLoading.value = true
        //favorite to unfavorite lists
        if(isOnFavorite.value){
            isOnFavorite.value = false
            movies.value = cachedMovies.value
            isLoading.value = false
        //unfavorite to favorite lists
        }else{
            isOnFavorite.value = true
            getAllFavoriteMovies()
            searchMovieLocal()
            movies.value = favoriteMovies.value
        }
    }

    //Get all favorite movies
    fun getAllFavoriteMovies()=viewModelScope.launch(Dispatchers.IO){
        localMovieRepository.getAllFavoriteMovies().distinctUntilChanged().collect { result->
            try{
                if(result.isNotEmpty()){
                    favoriteMovies.value = result
                    if(isOnFavorite.value){
                        if(searchQuery.value.isEmpty()){
                            movies.value = favoriteMovies.value
                        }
                    }
                    isLoading.value = false
                }else{
                    favoriteMovies.value = listOf()
                    favoriteMovieIds.value = listOf()
                    if(isOnFavorite.value) {
                        movies.value = listOf()
                    }
                    isLoading.value = false
                }
            }catch (e:Exception){
            }
        }
    }

    //Search favorite movies from the local database
    fun searchMovieLocal()=viewModelScope.launch {
        localMovieRepository.searchFavoriteMovie("%${searchQuery.value}%").distinctUntilChanged().collect {result->
            try{
                if(result.isNotEmpty()){
                    favoriteMovies.value = result
                    if(isOnFavorite.value){
                        if(searchQuery.value.isNotEmpty()){
                            movies.value = favoriteMovies.value
                        }
                    }
                }
            }catch (e:Exception){
            }
        }
    }

    //Search movies from the iTunesApi
    suspend fun newSearch(){
        viewModelScope.launch(Dispatchers.Default) {
            isLoading.value = true
            cachedMovies.value = listOf()//resetSearch
            val result = repository.getSearchMovieList(searchQuery.value)
            isLoading.value = false
            when(result){
                is Resource.Success->{
                    cachedMovies.value = result.data!!
                    movies.value = cachedMovies.value
                    isLoading.value = false
                }
                is Resource.Error->{
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading->{
                }
            }
        }
    }

    //Remove favorite movie from the local database
    suspend fun removeFromFavorite(movie: Movie){
        if(!isAddingOrRemovingToDatabase.value){
            isAddingOrRemovingToDatabase.value = true
            localMovieRepository.remove(movie.trackId)
            isAddingOrRemovingToDatabase.value = false
            getAllFavoriteMovies()

        }
    }

    //Add favorite movie to the local database
    suspend fun addToFavorites(movie:Movie){
        if(!isAddingOrRemovingToDatabase.value){
            isAddingOrRemovingToDatabase.value = true
            localMovieRepository.insert(movie)
            isAddingOrRemovingToDatabase.value = false
        }
    }
}