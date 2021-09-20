package com.example.tetrainingandroid.ui.main.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.Movie
import javax.inject.Inject

class MovieAdapter @Inject constructor(
    private val dividerItemDecoration: DividerItemDecoration,
    diffCallback: MovieDiffCallback
) : ListAdapter<Movie, MovieViewHolder>(diffCallback) {
    private var listener: MovieItemClickListener? = null

    fun setListener(listener: MovieItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder.from(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

}