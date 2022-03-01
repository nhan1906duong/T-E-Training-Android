package com.example.tetrainingandroid.ui.people.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.databinding.CrewItemLayoutBinding
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load

class CrewViewHolder (view: ViewGroup): PeopleViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): CrewViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.crew_item_layout, parent, false) as ViewGroup
                return CrewViewHolder(view)
            }
        }

    override fun bind(people: People, listener: PeopleItemClickListener?) {
        CrewItemLayoutBinding.bind(view).apply {
            rootLayout.setOnClickListener { people.id?.let { listener?.invoke(people) } }
            imgCrew.load(
                people.profilePath,
                size = ImageConfiguration.Size.PROFILE,
                type = ImageType.AVATAR
            )
            txtName.text = people.name ?: ""
            txtJob.text = people.job ?: ""
        }
    }
}