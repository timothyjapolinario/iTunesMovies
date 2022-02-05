package com.example.itunesmovies.data.remote.responses

import android.util.Log
import com.example.itunesmovies.models.Movie
import com.example.itunesmovies.models.util.DomainMapper

class ResultMapper: DomainMapper<Result, Movie>{
    override fun mapToDomainModel(model: Result): Movie {

        return Movie(
            artistName=model.artistName,
            artworkUrl100=model.artworkUrl100,
            artworkUrl30=model.artworkUrl30,
            artworkUrl60=model.artworkUrl60,
            longDescription=model.longDescription,
            shortDescription = model.shortDescription,
            primaryGenreName=model.primaryGenreName,
            trackHdPrice=model.trackHdPrice,
            trackId=model.trackId,
            trackName=model.trackCensoredName,
            trackPrice=model.trackPrice,
        )
    }
    fun toDomainList(initial: List<Result>): List<Movie>{
        return initial.map {
            mapToDomainModel(it)
        }
    }
}