package com.example.tetrainingandroid.ui.media.adapter.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.Youtube
import com.example.tetrainingandroid.databinding.YoutubeSelectableItemLayoutBinding
import com.example.tetrainingandroid.extensions.loadTrailer

class YoutubeSelectableViewHolder constructor(
    private val view: ViewGroup
): RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): YoutubeSelectableViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.youtube_selectable_item_layout, parent, false) as ViewGroup
            return YoutubeSelectableViewHolder(view)
        }
    }

    fun bind(youtube: Youtube, listener: YoutubeItemClickListener?, isSelected: Boolean) {
        YoutubeSelectableItemLayoutBinding.bind(view).apply {
            rootLayout.apply {
                if (isSelected) {
                    setBackgroundColor(ContextCompat.getColor(view.context, R.color.tertiaryColor))
                } else {
                    setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
                }
                setOnClickListener {
                    listener?.invoke(youtube)
                }
            }
            imgTrailer.loadTrailer(youtube.key)
            txtName.text = youtube.name ?: ""
            txtType.text = youtube.type ?: ""
        }
    }
}