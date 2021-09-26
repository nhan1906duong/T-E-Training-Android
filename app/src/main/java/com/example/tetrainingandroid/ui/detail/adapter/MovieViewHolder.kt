package com.example.tetrainingandroid.ui.detail.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load

abstract class MovieViewHolder(
    protected val view: ViewGroup
) : RecyclerView.ViewHolder(view) {
    open fun bind(movie: Movie, listener: MovieItemClickListener?, childItemClickListener: MovieChildItemClickListener?) {
        view.apply {
            findViewById<ConstraintLayout>(R.id.rootLayout)?.setOnClickListener {
                movie.id?.let { listener?.invoke(movie) }
            }
            findViewById<AppCompatImageView>(R.id.imgPoster)?.load(
                movie.posterPath,
                size = ImageConfiguration.Size.POSTER,
                type = ImageType.BACKGROUND
            )
            findViewById<TextView>(R.id.txtTitle)?.text= movie.title ?: ""
            findViewById<TextView>(R.id.txtFirstAirDate)?.text = movie.releaseDate ?: ""
        }
    }
}