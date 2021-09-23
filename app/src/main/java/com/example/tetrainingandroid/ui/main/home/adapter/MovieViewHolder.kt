package com.example.tetrainingandroid.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import kotlinx.android.synthetic.main.movie_item_layout.view.*

class MovieViewHolder private constructor(private val view: ViewGroup) :
    RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.movie_item_layout, parent, false) as ViewGroup
            return MovieViewHolder(view)
        }
    }

    fun bind(movie: Movie, listener: MovieItemClickListener?) {
        view.apply {
            rootLayout?.setOnClickListener {
                movie.id?.let { movieId -> listener?.onClick(movieId) }
            }
            imgPoster?.load(
                movie.posterPath,
                size = ImageConfiguration.Size.POSTER,
                type = ImageType.BACKGROUND
            )
            txtTitle?.text= movie.title ?: ""
            txtFirstAirDate?.text = movie.releaseDate ?: ""
        }
    }
}