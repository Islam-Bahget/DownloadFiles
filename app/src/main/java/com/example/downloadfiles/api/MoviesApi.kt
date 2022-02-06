package com.example.downloadfiles.api

import com.example.downloadfiles.model.MovieResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable


interface MoviesApi {


    @POST("movies")
    fun getMovies(): Observable<MovieResponse>
}