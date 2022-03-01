package com.example.tetrainingandroid.ui.main.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.Searchable
import com.example.tetrainingandroid.databinding.MovieSearchItemLayoutBinding
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load

class MovieSearchViewHolder(view: ViewGroup): SearchViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): SearchViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.movie_search_item_layout, parent, false) as ViewGroup
            return MovieSearchViewHolder(view)
        }
    }
    override fun bind(data: Searchable, listener: SearchItemClickListener?) {
        val movie = data as Movie
        MovieSearchItemLayoutBinding.bind(view).apply {
            rootLayout.setOnClickListener { movie.id?.let { listener?.invoke(movie) } }
            imgPoster.load(
                movie.posterPath,
                size = ImageConfiguration.Size.POSTER,
                type = ImageType.BACKGROUND
            )
            txtTitle.text = movie.title ?: view.context.getString(R.string.unknown)
            txtReleaseDate.text = movie.releaseDate ?: view.context.getString(R.string.unknown)
        }
    }
}