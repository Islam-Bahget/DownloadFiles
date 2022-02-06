package com.example.downloadfiles.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.downloadfiles.repository.HomeRepo
import javax.inject.Inject


class HomeViewModel @Inject constructor(var repo: HomeRepo) : ViewModel() {


    var moviesResponse = MutableLiveData<Any>()

    fun getMoviesFromServer() {
        repo.subScribe()
        moviesResponse = repo.getMovies()
    }

    //    @RequiresApi(Build.VERSION_CODES.O)
    fun getMoviesFromFile() {
        moviesResponse = repo.getMoviesFromFile()
    }

}