package com.example.moderndaytv.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.moderndaytv.R
import com.example.moderndaytv.model.MovieCard


class MovieAdapter(val movieListener: MovieSelectListener,
                   val movieCardList: ArrayList<MovieCard>): RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>() {

    override fun onBindViewHolder(holder: MovieAdapterViewHolder, position: Int) {
        val item = movieCardList[position]
        holder.bind(item, movieListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_card, parent, false)
        return MovieAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movieCardList.size
    }
    class MovieAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var movieImage = itemView.findViewById<ImageView>(R.id.movieImage)

        fun bind(movieCard: MovieCard, movieListener: MovieSelectListener){
            Glide
                .with(itemView)
                .load(movieCard.movie.image)
                .into(movieImage)
            itemView.setOnClickListener {
                if(movieCard != null ){
                    movieListener.onMovieCardSelect(movieCard.movie.id)
                }
            }
        }
    }
}