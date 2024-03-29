package com.example.itunesmovies.di

import android.content.Context
import androidx.room.Room
import com.example.itunesmovies.data.local.FavoriteMoviesDatabase
import com.example.itunesmovies.data.local.MovieDao
import com.example.itunesmovies.data.local.UserDao
import com.example.itunesmovies.data.remote.iTunesMovieApi.iTunesMovieApi
import com.example.itunesmovies.data.remote.responses.ResultMapper
import com.example.itunesmovies.repository.LocalMovieRepository
import com.example.itunesmovies.repository.NetworkMovieRepository
import com.example.itunesmovies.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun  provideMovieDao(database: FavoriteMoviesDatabase):MovieDao= database.getMovieDao()

    @Singleton
    @Provides
    fun  provideUserDao(database: FavoriteMoviesDatabase):UserDao= database.getUserDao()

    @Singleton
    @Provides
    fun provideLocalRepository(
        movieDao: MovieDao,
        userDao: UserDao
    ) = LocalMovieRepository(movieDao,userDao)

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context):FavoriteMoviesDatabase=
        Room.databaseBuilder(
            context,
            FavoriteMoviesDatabase::class.java,
            "favorite-db"
        ).fallbackToDestructiveMigration().build()
    @Singleton
    @Provides
    fun provideRepository(
        api: iTunesMovieApi,
        resultMapper: ResultMapper
    ) = NetworkMovieRepository(api, resultMapper)

    @Singleton
    @Provides
    fun provideApi(): iTunesMovieApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(iTunesMovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideResultMapper(): ResultMapper{
        return ResultMapper()
    }
}