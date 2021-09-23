package com.example.tetrainingandroid.ui.media.adapter.image

import androidx.recyclerview.widget.DiffUtil
import com.example.tetrainingandroid.data.model.Image
import javax.inject.Inject

class ImageDiffCallback @Inject constructor() : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Image, newItem: Image) = oldItem.filePath == newItem.filePath
}