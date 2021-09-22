package com.example.tetrainingandroid.ui.cast.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tetrainingandroid.data.model.People
import javax.inject.Inject

class CastDiffCallback @Inject constructor() : DiffUtil.ItemCallback<People>() {
    override fun areItemsTheSame(oldItem: People, newItem: People) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: People, newItem: People) =
        oldItem.name == newItem.name
                && oldItem.profilePath == newItem.profilePath
                && oldItem.character == newItem.character
}