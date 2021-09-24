package com.example.tetrainingandroid.ui.media.adapter.image

import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.Image

abstract class PhotoViewHolder(imageView: AppCompatImageView) :
    RecyclerView.ViewHolder(imageView) {
    abstract fun bind(data: Image, position: Int, listener: PhotoItemClickListener?)
}