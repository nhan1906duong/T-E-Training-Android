package com.example.tetrainingandroid.extensions

import android.view.View
import android.view.ViewGroup

fun View.removeFromParent(): Boolean {
    if (parent != null && parent is ViewGroup) {
        (parent as ViewGroup).removeView(this)
        return true
    }
    return false
}