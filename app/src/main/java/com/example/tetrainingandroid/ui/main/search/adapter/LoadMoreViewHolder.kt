package com.example.tetrainingandroid.ui.main.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.Searchable

class LoadMoreViewHolder(view: ViewGroup): SearchViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): SearchViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.load_more_layout, parent, false) as ViewGroup
            return LoadMoreViewHolder(view)
        }
    }

    override fun bind(data: Searchable, listener: SearchItemClickListener?) {

    }
}