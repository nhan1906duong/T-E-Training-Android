package com.example.tetrainingandroid.ui.main.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tetrainingandroid.data.model.Movie
import javax.inject.Inject

class TrendingMovieAdapter @Inject constructor(diffCallback: MovieDiffCallback):
    ListAdapter<Movie, TrendingMovieViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TrendingMovieViewHolder.from(parent)

    override fun onBindViewHolder(holder: TrendingMovieViewHolder, position: Int) = holder.bind(getItem(position))
}