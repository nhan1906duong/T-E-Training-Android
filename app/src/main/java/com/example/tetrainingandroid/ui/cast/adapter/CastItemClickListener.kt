package com.example.tetrainingandroid.ui.cast.adapter

class CastItemClickListener(val listener: (Int) -> Unit) {
    fun onClick(castId: Int) = listener(castId)
}