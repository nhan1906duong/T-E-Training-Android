package com.example.tetrainingandroid.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.Movie
import kotlinx.android.synthetic.main.favorite_movie_item_layout.view.*

class FavoriteMovieViewHolder(view: ViewGroup): MovieViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup) : MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.favorite_movie_item_layout, parent, false) as ViewGroup
            return FavoriteMovieViewHolder(view)
        }
    }

    override fun bind(movie: Movie, listener: MovieItemClickListener?, childItemClickListener: MovieChildItemClickListener?) {
        super.bind(movie, listener, childItemClickListener)
        view.imgFavorite?.setOnClickListener {
            childItemClickListener?.invoke(movie)
        }
    }
}