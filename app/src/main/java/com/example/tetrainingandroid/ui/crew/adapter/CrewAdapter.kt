package com.example.tetrainingandroid.ui.crew.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.di.DividerVertical16
import javax.inject.Inject

class CrewAdapter @Inject constructor(
    @DividerVertical16 private val dividerItemDecoration: DividerItemDecoration,
    diffCallback: CrewDiffCallback
): ListAdapter<People, CrewViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CrewViewHolder.from(parent)

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) = holder.bind(getItem(position))

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(dividerItemDecoration.apply {
            setOrientation(DividerItemDecoration.VERTICAL)
        })
    }
}