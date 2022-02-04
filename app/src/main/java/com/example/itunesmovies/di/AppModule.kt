package com.example.itunesmovies.di

import com.example.itunesmovies.data.iTunesMovieApi.iTunesMovieApi
import com.example.itunesmovies.repository.iTunesMovieRepository
import com.example.itunesmovies.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(
        api: iTunesMovieApi
    ) = iTunesMovieRepository(api)

    @Singleton
    @Provides
    fun provideApi(): iTunesMovieApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(iTunesMovieApi::class.java)
    }
}