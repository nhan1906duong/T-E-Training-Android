package com.example.tetrainingandroid.ui.detail.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tetrainingandroid.data.model.Movie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDiffCallback @Inject constructor() : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.images == newItem.images && oldItem.title == newItem.title
    }
}