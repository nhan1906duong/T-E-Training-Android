package com.example.tetrainingandroid.ui.main.home.adapter

class MovieItemClickListener(val listener: (Int) -> Unit) {
    fun onClick(movieId: Int) = listener(movieId)
}