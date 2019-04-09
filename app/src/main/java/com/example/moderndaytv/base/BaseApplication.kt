package com.example.moderndaytv.base

import android.app.Activity
import android.app.Application
import com.example.moderndaytv.di.AppModule
import com.example.moderndaytv.di.DaggerAppComponent
import com.example.moderndaytv.di.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BaseApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        val BASE_URL  = "https://us-central1-modern-venture-600.cloudfunctions.net/"
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule(BASE_URL))
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}