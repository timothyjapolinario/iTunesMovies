package com.example.itunesmovies.presentation.movielist

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.models.User
import com.example.itunesmovies.repository.LocalMovieRepository
import com.example.itunesmovies.repository.iTunesMovieRepository
import com.example.itunesmovies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel  @Inject constructor(
    private val repository: iTunesMovieRepository,
    private val localMovieRepository: LocalMovieRepository
): ViewModel(){
    val isOnFavorite = mutableStateOf(false)
    val favoriteMovieIds:MutableState<List<Int>> = mutableStateOf(listOf())
    val isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")
    var hasUser = ""
    var isAddingOrRemovingToDatabase = mutableStateOf(false)
    val movies:MutableState<List<Movie>> = mutableStateOf(listOf())
    val searchQuery = mutableStateOf("")
    val cachedMovies:MutableState<List<Movie>> = mutableStateOf(listOf())
    val favoriteCachedMovies: MutableState<List<Movie>> = mutableStateOf(listOf())
    val favoriteMovies:MutableState<List<Movie>> = mutableStateOf(listOf())

    /*
    * Get all favorite id for checking if favorite or not
    * Note: This gets called every recompose and needs to be fixed.
    * */
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

    fun onClickFavorite(){
        isLoading.value = true
        //favorite to unfavorite lists
        if(isOnFavorite.value){
            isOnFavorite.value = false
            movies.value = cachedMovies.value
            isLoading.value = false
            Log.i("TRACE:", "onClickFavorite true")
            Log.i("TRACE:", "movies -> cachedMovies")
        //unfavorite to favorite lists
        }else{
            isOnFavorite.value = true
            getAllFavoriteMovies()
            movies.value = favoriteMovies.value
            Log.i("TRACE:", "onClickFavorite else")
        }
    }
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
                }
            }catch (e:Exception){
            }
        }
    }
//    val listToSearch = movies.value
//    viewModelScope.launch {
//        if(searchQuery.value.isEmpty()){
//            movies.value = favoriteCachedMovies.value
//            return@launch
//        }
//        val result = listToSearch.filter{movie->
//            movie.trackName.contains(searchQuery.value.trim(), ignoreCase = true)
//        }
//        movies.value=result
//    }
    fun searchMovieLocal()=viewModelScope.launch {
        localMovieRepository.searchFavoriteMovie("%${searchQuery.value}%").distinctUntilChanged().collect {result->
            try{
                if(result.isNotEmpty()){
                    Log.i("TRACE:", result[0].trackName)
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
                    Log.i("MYLOGS: ", "loading")
                }
            }
        }
    }
    suspend fun removeFromFavorite(movie: Movie){
        if(!isAddingOrRemovingToDatabase.value){
            Log.i("MYLOGS: ", "Adding to database")
            isAddingOrRemovingToDatabase.value = true
            localMovieRepository.remove(movie.trackId)
            isAddingOrRemovingToDatabase.value = false
            getAllFavoriteMovies()

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