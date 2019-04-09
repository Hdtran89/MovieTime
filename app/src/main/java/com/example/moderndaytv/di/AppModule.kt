package com.example.moderndaytv.di

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import com.example.moderndaytv.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {
    @Singleton
    @Provides
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory = factory
}