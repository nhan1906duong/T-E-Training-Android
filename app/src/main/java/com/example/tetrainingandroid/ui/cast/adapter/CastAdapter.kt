package com.example.tetrainingandroid.ui.cast.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.di.DividerHorizontal16
import javax.inject.Inject

class CastAdapter @Inject constructor(
    @DividerHorizontal16 private val dividerItemDecoration: DividerItemDecoration,
    diffCallback: CastDiffCallback
): ListAdapter<People, CastViewHolder>(diffCallback) {
    private var listener: CastItemClickListener? = null

    fun setListener(listener: CastItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CastViewHolder.from(parent)

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) = holder.bind(getItem(position), listener)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }
}