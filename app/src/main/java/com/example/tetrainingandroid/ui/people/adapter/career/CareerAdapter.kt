package com.example.tetrainingandroid.ui.people.adapter.career

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tetrainingandroid.data.model.Career
import javax.inject.Inject

class CareerAdapter @Inject constructor(
    private val type: CareerViewHolderType,
    diffCallback: CareerDiffCallback
): ListAdapter<Career, CareerViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(type) {
        CareerViewHolderType.Cast -> CareerAsCastViewHolder.from(parent)
        CareerViewHolderType.Crew -> CareerAsCrewViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CareerViewHolder, position: Int) = holder.bind(getItem(position))
}