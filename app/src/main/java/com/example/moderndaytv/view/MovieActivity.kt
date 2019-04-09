package com.example.moderndaytv.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.example.moderndaytv.R
import com.example.moderndaytv.model.MovieCard
import com.example.moderndaytv.viewmodel.MovieCardViewModel
import com.example.moderndaytv.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class MovieActivity : AppCompatActivity(), MovieSelectListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var movieAdapter : MovieAdapter
    private lateinit var movieCardViewModel: MovieCardViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar : ProgressBar
    private val MOVIE_ID = "MovieID"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        AndroidInjection.inject(this)
        progressBar = findViewById(R.id.progressBar)
        movieCardViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            MovieCardViewModel::class.java)
        initializeRecycler()
        progressBar.visibility = View.VISIBLE
        loadData()
        observeViewModel()
    }

    fun observeViewModel(){
        movieCardViewModel.movieCardResult().observe(this, Observer {
            movieAdapter.movieCardList.addAll(it!!.toMutableList())
            movieAdapter.notifyDataSetChanged()
        })

        movieCardViewModel.movieCardError().observe(this, Observer {
            if (it != null) {
                Log.e("Error" ,it)
            }
        })

        movieCardViewModel.movieCardLoader().observe(this, Observer {
            if (it == false) progressBar.visibility = View.GONE
        })
    }

    fun loadData() {
        movieCardViewModel.fetchMovieCards()
    }

    private fun initializeRecycler() {
        val emptyArrayList = arrayListOf<MovieCard>()
        movieAdapter = MovieAdapter(this, emptyArrayList)
        val gridLayoutManager = GridLayoutManager(this, 1)
        recyclerView = findViewById(R.id.movieRecyclerView)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.adapter = movieAdapter
        recyclerView.apply {
            layoutManager = gridLayoutManager
        }
        recyclerView.setHasFixedSize(true)
    }

    override fun onMovieCardSelect(movieId: String) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_ID, movieId)
        startActivity(intent)
    }

    override fun onDestroy() {
        movieCardViewModel.disposeElements()
        super.onDestroy()
    }
}
