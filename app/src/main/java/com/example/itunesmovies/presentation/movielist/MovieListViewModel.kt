package com.example.itunesmovies.presentation.movielist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val favoriteMovieIds:MutableState<List<Int>> = mutableStateOf(listOf())
    val isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")
    var hasUser = ""
    var isAddingOrRemovingToDatabase = mutableStateOf(false)
    val movies:MutableState<List<Movie>> = mutableStateOf(listOf())
    val searchQuery = mutableStateOf("star")


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
    fun checkUser() {
        viewModelScope.launch(Dispatchers.IO){
            localMovieRepository.getAllFavoriteMovieId().distinctUntilChanged().collect() { result->
                try{
                    if(result.isEmpty()){
                        localMovieRepository.insertUser(User())
                        Log.i("MYLOGS: ", "INSERTED A USER!")
                    }
                }catch (e:Exception){
                }
            }
        }
    }
    suspend fun searchMovie(): Resource<List<Movie>> {
        return repository.getSearchMovieList("cat")
    }

    suspend fun newSearch(){
        viewModelScope.launch(Dispatchers.Default) {
            isLoading.value = true
            movies.value = listOf()//resetSearch
            val result = repository.getSearchMovieList(searchQuery.value)
            isLoading.value = false
            when(result){
                is Resource.Success->{
                    movies.value = result.data!!
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