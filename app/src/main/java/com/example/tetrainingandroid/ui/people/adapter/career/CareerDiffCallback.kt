package com.example.tetrainingandroid.ui.people.adapter.career

import androidx.recyclerview.widget.DiffUtil
import com.example.tetrainingandroid.data.model.Career
import javax.inject.Inject

class CareerDiffCallback @Inject constructor() : DiffUtil.ItemCallback<Career>() {
    override fun areItemsTheSame(oldItem: Career, newItem: Career) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Career,
        newItem: Career
    ) = oldItem.releaseDate == newItem.releaseDate
            && oldItem.title == newItem.title
            && (oldItem.character == newItem.character || oldItem.job == newItem.job)
}