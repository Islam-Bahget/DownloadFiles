package com.example.downloadfiles.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.downloadfiles.App
import com.example.downloadfiles.api.MoviesApi
import com.example.downloadfiles.model.MovieResponse
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import dagger.Module
import retrofit2.HttpException
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Inject
import javax.inject.Singleton

@Module
class HomeRepo @Inject constructor(var api: MoviesApi) {


    private var mCompositeSubscription: CompositeSubscription? = null
    var response = MutableLiveData<Any>()

    private fun configureComposition(): CompositeSubscription {
        if (mCompositeSubscription == null || mCompositeSubscription!!.isUnsubscribed) {
            mCompositeSubscription = CompositeSubscription()
        }
        return mCompositeSubscription!!
    }

    private var observer: Observer<MovieResponse> = object : Observer<MovieResponse> {
        override fun onCompleted() {
        }

        override fun onError(e: Throwable?) {

            response.value = e
        }

        override fun onNext(t: MovieResponse) {
            response.value = t
        }
    }

    @Singleton
    fun getMovies(): MutableLiveData<Any> {
        return response
    }

    fun subScribe() {

        val subscription = api.getMovies().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.computation())
            .subscribe(observer)
        configureComposition().add(subscription)

    }

//    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    fun getMoviesFromFile(): MutableLiveData<Any> {
        val gson = Gson()

        val reader = JsonReader(FileReader("getListOfFilesResponse.json"))

            //Files.newBufferedReader(Paths.get("getListOfFilesResponse.json"))
        response.value = gson.fromJson(reader, MovieResponse::class.java)
        return response
    }
}