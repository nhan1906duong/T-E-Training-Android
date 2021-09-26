package com.example.tetrainingandroid.ui.main.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.data.model.Searchable
import java.lang.IllegalArgumentException
import javax.inject.Inject

class SearchAdapter @Inject constructor(
    diffCallback: SearchDiffCallback
) : ListAdapter<Searchable, SearchViewHolder>(diffCallback) {
    companion object {
        private const val PEOPLE_TYPE = 1
        private const val MOVIE_TYPE = 2
        private const val LOAD_MORE_TYPE = 3
    }

    private var loadMoreHasEnd = false

    private var listener: SearchItemClickListener? = null
    private var onLoadMoreListener: OnLoadMoreListener? = null

    private var recyclerView: RecyclerView? = null
    private var isLoading = false

    fun setLoadMoreListener(listener: OnLoadMoreListener) {
        onLoadMoreListener = listener
    }

    fun setListener(listener: SearchItemClickListener) {
        this.listener = listener
    }

    fun setEndLoading() = true.also { loadMoreHasEnd = it }
    fun resetLoading() = false.also { loadMoreHasEnd = it }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        MOVIE_TYPE -> MovieSearchViewHolder.from(parent)
        PEOPLE_TYPE -> PeopleSearchViewHolder.from(parent)
        else -> LoadMoreViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        if (!isLoading  && !loadMoreHasEnd && isFullScreen()) {
            isLoading = true
            recyclerView?.post {
                onLoadMoreListener?.invoke()
            }
        }
        if (holder is LoadMoreViewHolder) return
        else holder.bind(getItem(position), listener)
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasLoadMore() && position == itemCount - 1) {
            LOAD_MORE_TYPE
        } else {
            when (getItem(position)) {
                is People -> PEOPLE_TYPE
                is Movie -> MOVIE_TYPE
                else -> throw IllegalArgumentException("Searchable type not support")
            }
        }
    }

    fun setList(list: List<Searchable>?) {
        isLoading = false
        val newData = mutableListOf<Searchable>()
        newData.apply {
            addAll(list ?: listOf())
            if (hasLoadMore()) add(LoadMoreSearchable())
        }
        submitList(newData)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    private fun hasLoadMore() = onLoadMoreListener != null
            && !loadMoreHasEnd

    private fun isFullScreen(): Boolean {
        val llm = recyclerView?.layoutManager as? LinearLayoutManager ?: return false
        return llm.findLastVisibleItemPosition() == itemCount - 2
    }
}