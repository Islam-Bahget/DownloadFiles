package com.example.downloadfiles

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.downloadfiles.network.*

class App : MultiDexApplication() {

    lateinit var apiComponent: ApiComponent
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        declareComponent()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun declareComponent() {

        apiComponent =
            DaggerApiComponent.builder().networkComponent(getNetworkComponent()).build()
    }

    private fun getNetworkComponent(): NetworkComponent {

        return DaggerNetworkComponent.builder()
            .retrofitClient(RetrofitClient(applicationContext))
            .build()
    }


}