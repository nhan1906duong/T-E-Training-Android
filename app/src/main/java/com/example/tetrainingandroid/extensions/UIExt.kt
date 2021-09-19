package com.example.tetrainingandroid.extensions

import android.widget.ImageView
import com.example.tetrainingandroid.config.Config
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.squareup.picasso.Picasso

fun ImageView.load(
    url: String?,
    type: ImageConfiguration.PosterSize = ImageConfiguration.PosterSize.REGULAR
) {
    if (url.isNullOrEmpty()) return
    Picasso.get().load(listOf(Config.BASE_IMAGE_URL, type.size, url).joinToString(separator = "/"))
        .into(this)
}