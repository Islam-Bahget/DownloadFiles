package com.example.downloadfiles.di

import com.example.downloadfiles.viewmodels.ViewModelModule
import dagger.Module

@Module(
    includes = [ViewModelModule::class,
        NetworkModule::class,
        RepoModule::class]
)
class AppModule