package com.example.tetrainingandroid.ui.media.adapter.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.adapter.ItemClickListener
import com.example.tetrainingandroid.data.model.Image
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load

class BackdropViewHolder(
    private val imageView: AppCompatImageView): PhotoViewHolder(imageView) {
        companion object {
            fun from(parent: ViewGroup): BackdropViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.backdrop_item_layout, parent, false) as AppCompatImageView
                return BackdropViewHolder(view)
            }
        }

    override fun bind(data: Image, position: Int, listener: ItemClickListener<Image>?) {
        imageView.apply {
            setOnClickListener { listener?.invoke(data, position) }
            load(
                data.filePath,
                size = ImageConfiguration.Size.BACKDROP,
                type = ImageType.BACKGROUND
            )
        }
    }
}