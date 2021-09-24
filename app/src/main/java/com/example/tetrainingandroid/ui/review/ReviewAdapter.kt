package com.example.tetrainingandroid.ui.review

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tetrainingandroid.data.model.Review
import javax.inject.Inject

class ReviewAdapter @Inject constructor(
    diffCallback: ReviewDiffCallback
): ListAdapter<Review, ReviewViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReviewViewHolder.from(parent)

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) = holder.bind(getItem(position))
}