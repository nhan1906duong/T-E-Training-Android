package com.example.tetrainingandroid.ui.genre.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.Genre
import com.example.tetrainingandroid.di.DividerHorizontal8
import javax.inject.Inject

class GenreAdapter @Inject constructor(
    @DividerHorizontal8 private val dividerItemDecoration: DividerItemDecoration,
    diffCallback: GenreDiffCallback
): ListAdapter<Genre, GenreViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GenreViewHolder.from(parent)

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) = holder.bind(getItem(position))

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeItemDecoration(dividerItemDecoration)
    }
}