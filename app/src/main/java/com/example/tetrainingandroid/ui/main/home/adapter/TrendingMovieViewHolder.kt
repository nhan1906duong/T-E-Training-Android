package com.example.tetrainingandroid.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.extensions.load
import kotlinx.android.synthetic.main.trending_movie_item_layout.view.*

class TrendingMovieViewHolder (private val view: ViewGroup): RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): TrendingMovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.trending_movie_item_layout, parent, false) as ViewGroup
            return TrendingMovieViewHolder(view)
        }
    }

    fun bind(movie: Movie) {
        view.imgBackdrop?.load(movie.backdropPath, ImageConfiguration.Size.BACKDROP)
    }
}