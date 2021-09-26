package com.example.tetrainingandroid.ui.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.Movie

class MovieAdapter(
    private val type: MovieViewHolderType,
    diffCallback: MovieDiffCallback,
    private val decorations: List<DividerItemDecoration>
) : ListAdapter<Movie, MovieViewHolder>(diffCallback) {
    private var listener: MovieItemClickListener? = null
    private var childListenerItem: MovieChildItemClickListener? = null

    fun setListener(listener: MovieItemClickListener) {
        this.listener = listener
    }

    fun setChildItemListener(listener: MovieChildItemClickListener) {
        this.childListenerItem = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(type) {
        MovieViewHolderType.FAVORITE -> FavoriteMovieViewHolder.from(parent)
        MovieViewHolderType.WATCHLIST -> WatchlistMovieViewHolder.from(parent)
        MovieViewHolderType.LIST -> ListMovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(getItem(position), listener, childListenerItem)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        for(decoration in decorations) {
            recyclerView.addItemDecoration(decoration)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        for(decoration in decorations) {
            recyclerView.removeItemDecoration(decoration)
        }
    }
}