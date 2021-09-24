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
) : ListAdapter<Youtube, YoutubeViewHolder>(diffCallback) {
    private var listener: YoutubeItemClickListener? = null

    fun setListener(listener: YoutubeItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        YoutubeViewHolder.from(parent)

    override fun onBindViewHolder(holder: YoutubeViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeItemDecoration(dividerItemDecoration)
    }
}