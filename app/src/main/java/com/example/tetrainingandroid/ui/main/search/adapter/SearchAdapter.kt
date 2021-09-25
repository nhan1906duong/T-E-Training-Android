package com.example.tetrainingandroid.ui.main.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.data.model.Searchable
import javax.inject.Inject

class SearchAdapter @Inject constructor(
    diffCallback: SearchDiffCallback
): ListAdapter<Searchable, SearchViewHolder>(diffCallback) {
    companion object {
        private const val PEOPLE_TYPE = 1
        private const val MOVIE_TYPE = 2
        private const val LOAD_MORE_TYPE = 3
    }

    private var listener: SearchItemClickListener? = null

    fun setListener(listener: SearchItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType) {
        MOVIE_TYPE -> MovieSearchViewHolder.from(parent)
        PEOPLE_TYPE -> PeopleSearchViewHolder.from(parent)
        else -> LoadMoreViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = holder.bind(getItem(position), listener)

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is People -> PEOPLE_TYPE
            is Movie -> MOVIE_TYPE
            else -> LOAD_MORE_TYPE
        }
    }
}