package com.example.tetrainingandroid.ui.main.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.Searchable

abstract class SearchViewHolder(
    protected val view: ViewGroup
): RecyclerView.ViewHolder(view) {
    abstract fun bind(data: Searchable, listener: SearchItemClickListener?)
}