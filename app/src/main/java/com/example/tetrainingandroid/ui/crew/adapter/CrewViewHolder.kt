package com.example.tetrainingandroid.ui.crew.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.ui.cast.adapter.CastItemClickListener
import kotlinx.android.synthetic.main.crew_item_layout.view.*
import kotlinx.android.synthetic.main.crew_item_layout.view.txtName
import javax.inject.Inject

class CrewViewHolder @Inject constructor(
    private val view: ViewGroup): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): CrewViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.crew_item_layout, parent, false) as ViewGroup
                return CrewViewHolder(view)
            }
        }

    fun bind(cast: People, listener: CastItemClickListener?) {
        view.apply {
            rootLayout?.setOnClickListener { cast.id?.let { listener?.onClick(it) } }
            imgCrew?.load(
                cast.profilePath,
                size = ImageConfiguration.Size.PROFILE,
                type = ImageType.AVATAR
            )
            txtName?.text = cast.name ?: ""
            txtJob?.text = cast.job ?: ""
        }
    }
}