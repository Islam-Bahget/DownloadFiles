package com.example.downloadfiles.di

import com.example.downloadfiles.api.MoviesApi
import com.example.downloadfiles.repository.HomeRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun providesRepo(moviesApi: MoviesApi): HomeRepo =
        HomeRepo(moviesApi)
}