package com.example.moderndaytv.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.moderndaytv.R
import com.example.moderndaytv.viewmodel.MovieCardViewModel
import com.example.moderndaytv.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class MovieDetailsActivity: AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val MOVIE_ID = "MovieID"
    private lateinit var movieCardViewModel: MovieCardViewModel
    private lateinit var movieImage: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieId: TextView
    private lateinit var progressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        AndroidInjection.inject(this)
        movieCardViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            MovieCardViewModel::class.java)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        progressBar = findViewById(R.id.progressBar)
        movieId = findViewById(R.id.movieId)
        movieImage = findViewById(R.id.movieImage)
        movieTitle = findViewById(R.id.movieTitle)
        progressBar.visibility = View.VISIBLE
        observeViewModel()
        val extras = intent.extras
        if(extras != null)
        {
            val movieId = extras.getString(MOVIE_ID)
            loadData(movieId!!)
        }
    }

    fun observeViewModel(){
        movieCardViewModel.movieCardDetailResult().observe(this, Observer {
            movieId.text = "ID: " + it?.movie?.id.orEmpty()
            Glide
                .with(this)
                .load(it?.movie?.image)
                .fitCenter()
                .into(movieImage)
            movieTitle.text = "Title: " + it?.movie?.title.orEmpty()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun loadData(movieId: String) {
        movieCardViewModel.fetchMovieCardDetails(movieId)
    }

    override fun onDestroy() {
        movieCardViewModel.disposeElements()
        super.onDestroy()
    }
}