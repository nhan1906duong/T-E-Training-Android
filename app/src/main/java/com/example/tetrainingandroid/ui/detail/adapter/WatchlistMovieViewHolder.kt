package com.example.tetrainingandroid.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.Movie

class WatchlistMovieViewHolder(view: ViewGroup): MovieViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup) : MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.watchlist_movie_item_layout, parent, false) as ViewGroup
            return WatchlistMovieViewHolder(view)
        }
    }

    override fun bind(movie: Movie, listener: MovieItemClickListener?, childItemClickListener: MovieChildItemClickListener?) {
        super.bind(movie, listener, childItemClickListener)
        view.findViewById<View>(R.id.imgFavorite)?.setOnClickListener {
            childItemClickListener?.invoke(movie)
        }
    }
}