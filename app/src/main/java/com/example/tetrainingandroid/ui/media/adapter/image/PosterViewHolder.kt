package com.example.tetrainingandroid.ui.media.adapter.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.Image
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load

class PosterViewHolder(
    private val imageView: AppCompatImageView
) : PhotoViewHolder(imageView) {
    companion object {
        fun from(parent: ViewGroup): PosterViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view =
                inflater.inflate(R.layout.poster_item_layout, parent, false) as AppCompatImageView
            return PosterViewHolder(view)
        }
    }

    override fun bind(data: Image, position: Int, listener: PhotoItemClickListener?) {
        imageView.apply {
            setOnClickListener { listener?.invoke(data, position) }
            load(
                data.filePath,
                size = ImageConfiguration.Size.POSTER,
                type = ImageType.BACKGROUND
            )
        }
    }
}