package com.example.itunesmovies.data.responses

data class iTuneMovie(
    val artistName: String,
    val artworkUrl100: String,
    val artworkUrl30: String,
    val artworkUrl60: String,
    val collectionExplicitness: String,
    val collectionHdPrice: Double,
    val collectionPrice: Double,
    val contentAdvisoryRating: String,
    val country: String,
    val currency: String,
    val kind: String,
    val longDescription: String,
    val previewUrl: String,
    val primaryGenreName: String,
    val releaseDate: String,
    val shortDescription: String,
    val trackCensoredName: String,
    val trackExplicitness: String,
    val trackHdPrice: Double,
    val trackHdRentalPrice: Double,
    val trackId: Int,
    val trackName: String,
    val trackPrice: Double,
    val trackRentalPrice: Double,
    val trackTimeMillis: Int,
    val trackViewUrl: String,
    val wrapperType: String
)