package com.example.tetrainingandroid.ui.media.adapter.video

import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.Youtube
import com.example.tetrainingandroid.di.DividerHorizontal8
import javax.inject.Inject

class YoutubeAdapter @Inject constructor(
    @DividerHorizontal8 private val dividerItemDecoration: DividerItemDecoration,
    diffCallback: YoutubeDiffCallback
): ListAdapter<Youtube, YoutubeViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = YoutubeViewHolder.from(parent)

    override fun onBindViewHolder(holder: YoutubeViewHolder, position: Int) = holder.bind(getItem(position))

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }
}