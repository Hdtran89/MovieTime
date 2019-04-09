package com.example.moderndaytv.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.moderndaytv.model.MovieCard
import com.example.moderndaytv.model.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieCardViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var movieCardListResult : MutableLiveData<List<MovieCard>> = MutableLiveData()
    private var movieCardDetailResult : MutableLiveData<MovieCard> = MutableLiveData()
    private var movieCardError : MutableLiveData<String> = MutableLiveData()
    private var movieCardLoader: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var disposableObserverMovieCardList: DisposableObserver<List<MovieCard>>
    lateinit var disposableObserverMovieCard: DisposableObserver<MovieCard>

    fun movieCardResult(): LiveData<List<MovieCard>> {
        return movieCardListResult
    }

    fun movieCardDetailResult(): LiveData<MovieCard>{
        return movieCardDetailResult
    }

    fun movieCardError(): LiveData<String>{
        return movieCardError
    }

    fun movieCardLoader(): LiveData<Boolean>{
        return movieCardLoader
    }

    fun fetchMovieCards(){
        disposableObserverMovieCardList = object : DisposableObserver<List<MovieCard>>(){
            override fun onComplete() {

            }

            override fun onError(e: Throwable) {
                movieCardError.postValue(e.message)
                movieCardLoader.postValue(false)
            }

            override fun onNext(movieCardList: List<MovieCard>) {
                movieCardListResult.postValue(movieCardList)
                movieCardLoader.postValue(false)
            }
        }

        movieRepository.getMovieCardListApi()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserverMovieCardList)
    }

    fun fetchMovieCardDetails(movie_id:String){
        disposableObserverMovieCard = object : DisposableObserver<MovieCard>(){
            override fun onComplete() {

            }

            override fun onError(e: Throwable) {
                movieCardError.postValue(e.message)
                movieCardLoader.postValue(false)
            }

            override fun onNext(movieCard: MovieCard?) {
                movieCardDetailResult.postValue(movieCard)
                movieCardLoader.postValue(false)
            }
        }

        movieRepository.getMovieCardDetailsApi(movie_id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserverMovieCard)
    }
    fun disposeElements(){
        if(null != disposableObserverMovieCard && !disposableObserverMovieCard.isDisposed)
            disposableObserverMovieCard.dispose()
        else if(null != disposableObserverMovieCardList && !disposableObserverMovieCardList.isDisposed)
            disposableObserverMovieCardList.dispose()
    }
}