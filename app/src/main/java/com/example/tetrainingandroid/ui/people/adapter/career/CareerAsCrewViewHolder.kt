package com.example.tetrainingandroid.ui.people.adapter.career

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.Career
import kotlinx.android.synthetic.main.career_as_crew_item_layout.view.*

class CareerAsCrewViewHolder(
    view: ViewGroup
) : CareerViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): CareerViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.career_as_crew_item_layout, parent, false) as ViewGroup
            return CareerAsCrewViewHolder(view)
        }
    }

    override fun bind(career: Career) {
        view.apply {
            txtYear?.text = career.releaseDate?.split("-")?.firstOrNull() ?: "-"
            txtMovieTitle?.text = career.title ?: context.getString(R.string.unknown)
            txtJob?.text = career.job ?: context.getString(R.string.unknown)
        }
    }
}