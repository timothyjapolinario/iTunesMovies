package com.example.itunesmovies.repository
import com.example.itunesmovies.data.iTunesMovieApi.iTunesMovieApi
import com.example.itunesmovies.data.responses.SearchResult
import com.example.itunesmovies.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class iTunesMovieRepository @Inject constructor(
    val iTuneMovieApi: iTunesMovieApi
) {
    suspend fun getMovieInfo(id: Int): Resource<SearchResult>{
        val response = try {
            iTuneMovieApi.getMovie(id)
        }catch(e:Exception){
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    suspend fun getSearchMovie(term: String, media: String): Resource<SearchResult>{
        val response = try {
            iTuneMovieApi.getMovieList(term, media)
        }catch(e:Exception){
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}