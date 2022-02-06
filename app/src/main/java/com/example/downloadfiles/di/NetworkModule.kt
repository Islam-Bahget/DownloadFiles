package com.example.downloadfiles.network

import android.content.Context
import com.example.downloadfiles.api.MoviesApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitClient(private val context: Context) {

    private val BASE_URL = "https://nagwa.free.beeceptor.com/"
    private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun getRetrofit(): MoviesApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(getClient())
            .addConverterFactory(getConverterFactory())
            .addCallAdapterFactory(getRxAdapter())
            .build().create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun getClient(): OkHttpClient {
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun getConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun getRxAdapter(): RxJavaCallAdapterFactory {
        return RxJavaCallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun getContext(): Context {
        return context
    }
}