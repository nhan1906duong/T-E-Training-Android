package com.example.tetrainingandroid.ui.media.adapter.image

import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.Image
import com.example.tetrainingandroid.di.DividerHorizontal8
import javax.inject.Inject

class BackdropAdapter @Inject constructor(
    @DividerHorizontal8 private val dividerItemDecoration: DividerItemDecoration,
    diffCallback: BackdropDiffCallback
): ListAdapter<Image, BackdropViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BackdropViewHolder.from(parent)

    override fun onBindViewHolder(holder: BackdropViewHolder, position: Int) = holder.bind(getItem(position))

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }
}