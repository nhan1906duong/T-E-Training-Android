package com.example.tetrainingandroid.ui.people.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import kotlinx.android.synthetic.main.cast_item_layout.view.*

class CastViewHolder(
    view: ViewGroup
) : PeopleViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): CastViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.cast_item_layout, parent, false) as ViewGroup
            return CastViewHolder(view)
        }
    }

    override fun bind(people: People, listener: PeopleItemClickListener?) {
        view.apply {
            rootLayout?.setOnClickListener { people.id?.let { listener?.invoke(people) } }
            imgCast?.load(
                people.profilePath,
                size = ImageConfiguration.Size.PROFILE,
                type = ImageType.AVATAR
            )
            txtName?.text = people.name ?: ""
            txtCharacter?.text = people.character ?: ""
        }
    }
}