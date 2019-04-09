package com.example.moderndaytv.di

import com.example.moderndaytv.base.BaseApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules =
            [   AppModule::class,
                NetworkModule::class,
                ContextModule::class,
                ActivityBuilder::class
            ])

interface AppComponent {
    fun inject(app: BaseApplication)
}