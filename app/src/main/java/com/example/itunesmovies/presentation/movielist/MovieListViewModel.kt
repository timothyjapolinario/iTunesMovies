package com.example.itunesmovies.presentation.movielist

import androidx.lifecycle.ViewModel
import com.example.itunesmovies.repository.iTunesMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel  @Inject constructor(
    private val repository: iTunesMovieRepository
): ViewModel(){
    fun searchMovie(){

    }
}