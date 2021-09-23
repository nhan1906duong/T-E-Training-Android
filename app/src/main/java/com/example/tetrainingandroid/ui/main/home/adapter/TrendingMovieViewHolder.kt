package com.example.tetrainingandroid.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load

class TrendingMovieViewHolder (private val imageView: AppCompatImageView): RecyclerView.ViewHolder(imageView) {
    companion object {
        fun from(parent: ViewGroup): TrendingMovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.trending_movie_item_layout, parent, false) as AppCompatImageView
            return TrendingMovieViewHolder(view)
        }
    }

    fun bind(movie: Movie, listener: MovieItemClickListener?) {
        imageView.apply {
            setOnClickListener {
                movie.id?.let { movieId -> listener?.onClick(movieId) }
            }
            load(
                movie.backdropPath,
                size = ImageConfiguration.Size.BACKDROP,
                type = ImageType.BACKGROUND
            )
        }
    }
}