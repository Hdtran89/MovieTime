package com.example.moderndaytv.model

import io.reactivex.Observable
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieService: MovieService
){
    private val lruCache = LRUCache()
    fun getMovieCardDetailsApi(movie_id: String) : Observable<MovieCard>{
        val item = lruCache.getItem(movie_id)
        if(item != null){
            return Observable.just(item)
        } else {
            return getMovieDetails(movie_id).flatMap {
                Observable.just(MovieCard(it))
            }
        }
    }

    fun getMovieCardListApi() : Observable<List<MovieCard>> {
        return getMovies()
            .flatMap { Observable.fromIterable(it) }
            .map { MovieCard(it) }
            .toList()
            .toObservable()
    }

    private fun getMovies() : Observable<List<Movie>>{
        return movieService.getMovies()
    }

    private fun getMovieDetails(movie_id: String): Observable<Movie>{
        return movieService.getMovieDetails(movie_id).doOnNext {
            lruCache.putItem(movie_id, MovieCard(it))
        }
    }
}