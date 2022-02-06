package com.example.downloadfiles.repository

import androidx.lifecycle.MutableLiveData
import com.example.downloadfiles.api.MoviesApi
import com.example.downloadfiles.model.MovieResponse
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import dagger.Module
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Inject
import javax.inject.Singleton

@Module
class HomeRepo @Inject constructor(var api: MoviesApi) {

    var response = MutableLiveData<Any>()

    private var mCompositeSubscription: CompositeSubscription? = null

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


}