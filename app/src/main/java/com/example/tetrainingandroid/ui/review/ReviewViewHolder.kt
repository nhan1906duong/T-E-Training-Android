package com.example.tetrainingandroid.ui.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.Review
import com.example.tetrainingandroid.databinding.ReviewItemLayoutBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ReviewViewHolder(
    private val view: ViewGroup
) : RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): ReviewViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.review_item_layout, parent, false) as ViewGroup
            return ReviewViewHolder(view)
        }
    }

    fun bind(review: Review) {
        ReviewItemLayoutBinding.bind(view).apply {
            val context = view.context
            txtName.text = HtmlCompat.fromHtml(
                String.format(
                    context.getString(R.string.by_author),
                    review.authorUsername ?: context.getString(R.string.unknown)
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            val date = review.updatedAt ?: review.createdAt
            if (date == null) {
                txtDate.visibility = View.GONE
            } else {
                txtDate.visibility = View.VISIBLE
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                txtDate.text = formatter.format(date)
            }
            txtRating.text = String.format("%.1f", review.author?.rating ?: 0.0)
            txtContent.text = review.content ?: ""
            var avatarPath = review.author?.avatarPath ?: return@apply
            if (avatarPath.startsWith("/")) {
                avatarPath = avatarPath.substring(1)
                Picasso.get()
                    .load(avatarPath)
                    .placeholder(R.drawable.image_placeholder_background)
                    .error(R.drawable.error_avatar_placeholder)
                    .into(imgAvatar)
            }
        }
    }
}