package com.example.tetrainingandroid.extensions

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tetrainingandroid.config.Config
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.squareup.picasso.Picasso

fun ImageView.load(
    url: String?,
    type: ImageConfiguration.Size = ImageConfiguration.Size.POSTER
) {
    if (url.isNullOrEmpty()) return
    Picasso.get().load(listOf(Config.BASE_IMAGE_URL, type.size, url).joinToString(separator = "/"))
        .into(this)
}

fun Fragment.toast(message: String?) {
    if (context == null || message.isNullOrEmpty()) return
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Activity.getScreenWidth(): Int? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager?.currentWindowMetrics
        val insets = windowMetrics?.windowInsets?.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        val width = windowMetrics?.bounds?.width()
        if (width == null || insets == null) return null
        return width - insets.left - insets.right
    }
    else {
        val metrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager?.defaultDisplay?.getMetrics(metrics)
        return metrics.widthPixels

    }
}