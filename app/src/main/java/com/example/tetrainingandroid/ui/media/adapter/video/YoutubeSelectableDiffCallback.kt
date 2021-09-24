package com.example.tetrainingandroid.ui.media.adapter.video

import androidx.recyclerview.widget.DiffUtil
import com.example.tetrainingandroid.data.model.Youtube
import javax.inject.Inject

class YoutubeSelectableDiffCallback @Inject constructor() : DiffUtil.ItemCallback<Youtube>() {
    override fun areItemsTheSame(oldItem: Youtube, newItem: Youtube) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Youtube,
        newItem: Youtube
    ) = oldItem.key == newItem.key
                && oldItem.name == newItem.name
                && oldItem.type == newItem.type
}