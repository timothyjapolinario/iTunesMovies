package com.example.itunesmovies.presentation.movielist

import androidx.lifecycle.ViewModel
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.repository.iTunesMovieRepository
import com.example.itunesmovies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel  @Inject constructor(
    private val repository: iTunesMovieRepository
): ViewModel(){
    suspend fun searchMovie(): Resource<List<Movie>> {
        return repository.getSearchMovie("cat")
    }
}