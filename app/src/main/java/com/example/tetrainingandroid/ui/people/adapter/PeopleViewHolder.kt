package com.example.tetrainingandroid.ui.people.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.People

abstract class PeopleViewHolder(
    protected val view: ViewGroup
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(people: People, listener: PeopleItemClickListener?)
}