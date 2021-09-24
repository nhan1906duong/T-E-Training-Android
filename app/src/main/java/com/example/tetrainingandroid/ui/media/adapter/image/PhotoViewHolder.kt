package com.example.tetrainingandroid.ui.media.adapter.image

import androidx.appcompat.widget.AppCompatImageView
import com.example.tetrainingandroid.architecture.adapter.BaseViewHolder
import com.example.tetrainingandroid.architecture.adapter.ItemClickListener
import com.example.tetrainingandroid.data.model.Image

abstract class PhotoViewHolder(imageView: AppCompatImageView) :
    BaseViewHolder<Image, ItemClickListener<Image>>(imageView)