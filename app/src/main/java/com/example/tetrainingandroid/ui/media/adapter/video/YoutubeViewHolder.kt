package com.example.tetrainingandroid.ui.media.adapter.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.Youtube
import com.example.tetrainingandroid.databinding.YoutubeItemLayoutBinding
import com.example.tetrainingandroid.extensions.loadTrailer

class YoutubeViewHolder(
    private val view: ViewGroup): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): YoutubeViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.youtube_item_layout, parent, false) as ViewGroup
                return YoutubeViewHolder(view)
            }
        }

    fun bind(youtube: Youtube, listener: YoutubeItemClickListener?) {
        YoutubeItemLayoutBinding.bind(view).apply {
            rootLayout.setOnClickListener {
                youtube.key?.let { listener?.invoke(youtube) }
            }
            imgTrailer.loadTrailer(youtube.key)
        }
    }
}