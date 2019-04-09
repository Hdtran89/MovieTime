package com.example.moderndaytv.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val movieCardViewModel: MovieCardViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieCardViewModel::class.java!!)) {
            return movieCardViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}