package com.example.tetrainingandroid.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tetrainingandroid.R

class ListMovieViewHolder(view: ViewGroup): MovieViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup) : MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.movie_item_layout, parent, false) as ViewGroup
            return WatchlistMovieViewHolder(view)
        }
    }
}