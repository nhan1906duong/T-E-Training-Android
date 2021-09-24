package com.example.tetrainingandroid.ui.main.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.data.model.Searchable
import javax.inject.Inject

class SearchDiffCallback @Inject constructor() : DiffUtil.ItemCallback<Searchable>() {
    override fun areItemsTheSame(
        oldItem: Searchable,
        newItem: Searchable
    ) = oldItem::class == newItem::class
            && ((oldItem is Movie && newItem is Movie && oldItem.id == newItem.id)
            || (oldItem is People && newItem is People && oldItem.id == newItem.id))

    override fun areContentsTheSame(oldItem: Searchable, newItem: Searchable): Boolean {
        if (oldItem is Movie && newItem is Movie) {
            return oldItem.title == newItem.title && oldItem.posterPath == newItem.posterPath
        }
        if (oldItem is People && newItem is People) {
            return oldItem.name == newItem.name && oldItem.profilePath == newItem.profilePath
        }
        return false
    }
}