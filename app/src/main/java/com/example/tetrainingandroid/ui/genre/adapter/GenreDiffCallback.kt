package com.example.tetrainingandroid.ui.genre.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tetrainingandroid.data.model.Genre
import javax.inject.Inject

class GenreDiffCallback @Inject constructor(): DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre) = oldItem.name == newItem.name
}