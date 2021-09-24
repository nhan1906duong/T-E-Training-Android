package com.example.tetrainingandroid.ui.people.adapter.career

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.Career

abstract class CareerViewHolder(
    protected val view: ViewGroup
) : RecyclerView.ViewHolder(view) {
    abstract fun bind(career: Career)
}