package com.example.moderndaytv.model

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET("/api/movies")
    fun getMovies() : Observable<List<Movie>>

    @GET("/api/movies/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movie_id: String) : Observable<Movie>
}