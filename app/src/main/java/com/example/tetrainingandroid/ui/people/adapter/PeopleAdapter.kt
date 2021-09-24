package com.example.tetrainingandroid.ui.people.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.People

class PeopleAdapter(
    private val type: PeopleViewHolderType,
    private val dividerItemDecoration: DividerItemDecoration,
    diffCallback: DiffUtil.ItemCallback<People>
) : ListAdapter<People, PeopleViewHolder>(diffCallback) {
    private var listener: PeopleItemClickListener? = null

    fun setListener(listener: PeopleItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (type) {
        PeopleViewHolderType.CAST -> CastViewHolder.from(parent)
        PeopleViewHolderType.CREW -> CastViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(dividerItemDecoration.apply {
            setOrientation(if (type == PeopleViewHolderType.CREW) DividerItemDecoration.VERTICAL else DividerItemDecoration.HORIZONTAL)
        })
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeItemDecoration(dividerItemDecoration)
    }
}