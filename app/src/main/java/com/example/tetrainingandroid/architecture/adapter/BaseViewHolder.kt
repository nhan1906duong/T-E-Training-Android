package com.example.tetrainingandroid.architecture.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T, L: BaseItemClickListener<T>>(
    protected val view: View
): RecyclerView.ViewHolder(view) {
    abstract fun bind(data: T, listener: L?)
}