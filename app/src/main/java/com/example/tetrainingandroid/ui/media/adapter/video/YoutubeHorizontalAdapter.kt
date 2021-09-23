package com.example.tetrainingandroid.ui.media.adapter.video

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tetrainingandroid.data.model.Youtube
import javax.inject.Inject

class YoutubeHorizontalAdapter @Inject constructor(
    diffCallback: YoutubeHorizontalDiffCallback
): ListAdapter<Youtube, YoutubeHorizontalViewHolder>(diffCallback) {

    private var selectedIndex: Int = 0
    private var listener: YoutubeItemClickListener? = null

    fun setListener(listener: YoutubeItemClickListener) {
        this.listener = listener
    }

    fun setSelected(index: Int) {
        if (selectedIndex != index) {
            notifyItemChanged(selectedIndex)
            notifyItemChanged(index)
            selectedIndex = index
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = YoutubeHorizontalViewHolder.from(parent)

    override fun onBindViewHolder(holder: YoutubeHorizontalViewHolder, position: Int) = holder.bind(getItem(position), listener, selectedIndex == position)
}