package com.example.tetrainingandroid.extensions

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.config.Config
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.squareup.picasso.Picasso

enum class ImageType {
    BACKGROUND,
    AVATAR
}

fun ImageView.loadTrailer(key: String?) {
    if (key.isNullOrEmpty()) return
    val url = String.format(Config.YOUTUBE_LINK, key)
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.image_placeholder_background)
        .error(R.drawable.error_background_placeholder)
        .into(this)
}

fun ImageView.load(
    path: String?,
    size: ImageConfiguration.Size = ImageConfiguration.Size.PROFILE,
    type: ImageType
) {
    val newPath: String = if (path?.startsWith("/") == true) {
        path.substring(1)
    } else {
        path ?: ""
    }
    val url = listOf(Config.BASE_IMAGE_URL, size.size, newPath).joinToString(separator = "/")
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.image_placeholder_background)
        .error(if (type == ImageType.AVATAR) R.drawable.error_avatar_placeholder else R.drawable.error_background_placeholder)
        .into(this)
}

fun Fragment.toast(message: String?) {
    if (context == null || message.isNullOrEmpty()) return
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Activity.getScreenWidth(): Int? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager?.currentWindowMetrics
        val insets =
            windowMetrics?.windowInsets?.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        val width = windowMetrics?.bounds?.width()
        if (width == null || insets == null) return null
        return width - insets.left - insets.right
    } else {
        val metrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager?.defaultDisplay?.getMetrics(metrics)
        return metrics.widthPixels

    }
}