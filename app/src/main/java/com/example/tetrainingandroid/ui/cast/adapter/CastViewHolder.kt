package com.example.tetrainingandroid.ui.cast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import kotlinx.android.synthetic.main.cast_item_layout.view.*
import javax.inject.Inject

class CastViewHolder @Inject constructor(
    private val view: ViewGroup): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): CastViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.cast_item_layout, parent, false) as ViewGroup
                return CastViewHolder(view)
            }
        }

    fun bind(cast: People) {
        view.apply {
            imgCast?.load(
                cast.profilePath,
                size = ImageConfiguration.Size.PROFILE,
                type = ImageType.AVATAR
            )
            txtName?.text = cast.name ?: ""
            txtCharacter?.text = cast.character ?: ""
        }
    }
}