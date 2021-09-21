package com.example.tetrainingandroid.ui.genre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.Genre

class GenreViewHolder (private val view: TextView): RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): GenreViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.genre_item_layout, parent, false) as TextView
            return GenreViewHolder(view)
        }
    }

    fun bind(genre: Genre) {
        view.text = genre.name ?: ""
    }
}