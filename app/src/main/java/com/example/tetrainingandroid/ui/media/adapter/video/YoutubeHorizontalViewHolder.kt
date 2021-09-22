package com.example.tetrainingandroid.ui.media.adapter.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.model.Youtube
import com.example.tetrainingandroid.extensions.loadTrailer
import kotlinx.android.synthetic.main.trailer_horizontal_item_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class YoutubeHorizontalViewHolder @Inject constructor(
    private val view: ViewGroup
): RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): YoutubeHorizontalViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.trailer_horizontal_item_layout, parent, false) as ViewGroup
            return YoutubeHorizontalViewHolder(view)
        }
    }

    fun bind(youtube: Youtube, listener: YoutubeItemClickListener?, isSelected: Boolean) {
        if (isSelected) {
            view.rootLayout?.setBackgroundColor(ContextCompat.getColor(view.context, R.color.tertiaryColor))
        } else {
            view.rootLayout?.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
        }
        view.rootLayout?.setOnClickListener {
            listener?.onClick(youtube)
        }
        view.imgTrailer?.loadTrailer(youtube.key)
        view.txtName?.text = youtube.name
        youtube.publishedAt?.let {
            view.txtTime?.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
        }
    }
}