package com.example.moderndaytv.di

import com.example.moderndaytv.view.MovieActivity
import com.example.moderndaytv.view.MovieDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMovieActivity(): MovieActivity

    @ContributesAndroidInjector
    abstract fun bindMovieDetailsActivity(): MovieDetailsActivity
}