package com.example.itunesmovies.repository
import com.example.itunesmovies.data.remote.iTunesMovieApi.iTunesMovieApi
import com.example.itunesmovies.data.remote.responses.ResultMapper
import com.example.itunesmovies.data.remote.responses.SearchResult
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class iTunesMovieRepository @Inject constructor(
    private val iTuneMovieApi: iTunesMovieApi,
    private val mapper: ResultMapper
) {
    suspend fun getMovieInfo(id: Int): Resource<Movie>{
        val response = try {
            iTuneMovieApi.getMovie(id)
        }catch(e:Exception){
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(mapper.mapToDomainModel(response.results[0]))
    }

    suspend fun getSearchMovie(term: String, media:String = "movie"): Resource<List<Movie>>{
        val response = try {
            iTuneMovieApi.getMovieList(term, media)
        }catch(e:Exception){
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(mapper.toDomainList(response.results))
    }
}