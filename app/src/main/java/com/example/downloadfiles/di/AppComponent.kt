package com.example.downloadfiles.network

import android.content.Context
import com.example.downloadfiles.ui.HomeRepo
import com.example.downloadfiles.ui.HomeViewModel
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitClient::class])
interface AppComponent {
    fun retrofit(): Retrofit

}