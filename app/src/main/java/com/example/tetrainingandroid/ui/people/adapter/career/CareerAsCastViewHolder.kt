package com.example.tetrainingandroid.ui.people.adapter.career

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.Career
import kotlinx.android.synthetic.main.career_as_cast_item_layout.view.*

class CareerAsCastViewHolder(
    view: ViewGroup
) : CareerViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): CareerViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view =
                inflater.inflate(R.layout.career_as_cast_item_layout, parent, false) as ViewGroup
            return CareerAsCastViewHolder(view)
        }
    }

    override fun bind(career: Career) {
        view.apply {
            txtYear?.text = career.releaseDate?.split("-")?.firstOrNull() ?: "-"
            txtMovieTitle?.text = career.title ?: context.getString(R.string.unknown)
            txtCharacter?.text = HtmlCompat.fromHtml(
                String.format(
                    context.getString(R.string.as_character),
                    career.character ?: context.getString(R.string.unknown)
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
    }
}