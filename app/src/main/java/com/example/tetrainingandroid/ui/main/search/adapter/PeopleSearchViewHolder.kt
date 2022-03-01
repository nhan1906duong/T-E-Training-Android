package com.example.tetrainingandroid.ui.main.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.data.model.Searchable
import com.example.tetrainingandroid.databinding.PeopleSearchItemLayoutBinding
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load

class PeopleSearchViewHolder(view: ViewGroup): SearchViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): SearchViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.people_search_item_layout, parent, false) as ViewGroup
            return PeopleSearchViewHolder(view)
        }
    }
    override fun bind(data: Searchable, listener: SearchItemClickListener?) {
        val people = data as People
        PeopleSearchItemLayoutBinding.bind(view).apply {
            rootLayout.setOnClickListener { people.id?.let { listener?.invoke(people) } }
            imgProfile.load(
                people.profilePath,
                size = ImageConfiguration.Size.PROFILE,
                type = ImageType.BACKGROUND
            )
            txtName.text = people.name ?: view.context.getString(R.string.unknown)
            txtKnownFor.text = people.knownForDepartment ?: view.context.getString(R.string.unknown)
        }
    }
}