package com.example.tetrainingandroid.ui.review

import androidx.recyclerview.widget.DiffUtil
import com.example.tetrainingandroid.data.model.Review
import javax.inject.Inject

class ReviewDiffCallback @Inject constructor(): DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Review,
        newItem: Review
    ) = oldItem.content == newItem.content
            && oldItem.authorUsername == newItem.authorUsername
            && oldItem.author?.avatarPath == newItem.author?.avatarPath
            && oldItem.author?.rating == newItem.author?.rating
            && oldItem.updatedAt == newItem.updatedAt
}