package com.example.tetrainingandroid.ui.media.adapter.image

import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.data.model.Image
import com.example.tetrainingandroid.di.DividerHorizontal8
import javax.inject.Inject

class PhotoAdapter @Inject constructor(
    @DividerHorizontal8 private val dividerItemDecoration: DividerItemDecoration,
    diffCallback: ImageDiffCallback
):
    ListAdapter<Image, PhotoViewHolder>(diffCallback) {

    private var type = PhotoViewHolderType.BACKDROP

    private var listener: PhotoItemClickListener? = null

    fun setListener(listener: PhotoItemClickListener) {
        this.listener = listener
    }

    fun setProfileType()  {
        type = PhotoViewHolderType.PROFILE
    }


    fun setType(type: PhotoViewHolderType)  {
        this.type = type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return when(type) {
            PhotoViewHolderType.BACKDROP -> BackdropViewHolder.from(parent)
            PhotoViewHolderType.POSTER -> BackdropViewHolder.from(parent)
            PhotoViewHolderType.PROFILE -> ProfileViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) = holder.bind(getItem(position), listener)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeItemDecoration(dividerItemDecoration)
    }
}







