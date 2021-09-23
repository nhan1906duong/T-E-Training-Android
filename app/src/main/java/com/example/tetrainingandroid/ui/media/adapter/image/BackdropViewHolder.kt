package com.example.tetrainingandroid.ui.media.adapter.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.Image
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import javax.inject.Inject

class BackdropViewHolder @Inject constructor(
    private val imageView: AppCompatImageView): RecyclerView.ViewHolder(imageView) {
        companion object {
            fun from(parent: ViewGroup): BackdropViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.backdrop_item_layout, parent, false) as AppCompatImageView
                return BackdropViewHolder(view)
            }
        }

    fun bind(cast: Image) {
        imageView.load(
            cast.filePath,
            size = ImageConfiguration.Size.PROFILE,
            type = ImageType.AVATAR
        )
    }
}