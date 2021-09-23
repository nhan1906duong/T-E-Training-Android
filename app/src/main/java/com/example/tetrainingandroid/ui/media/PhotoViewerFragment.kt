package com.example.tetrainingandroid.ui.media

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.BaseFragment
import com.example.tetrainingandroid.data.model.Image
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.ui.media.adapter.image.PhotoAdapter
import com.example.tetrainingandroid.ui.media.adapter.image.PhotoItemClickListener
import com.example.tetrainingandroid.ui.media.adapter.image.PhotoViewHolderType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.photo_viewer_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class PhotoViewerFragment : BaseFragment(R.layout.photo_viewer_fragment) {
    @Inject
    lateinit var photoAdapter: PhotoAdapter

    private val args: PhotoViewerFragmentArgs by navArgs()

    private val image = MutableLiveData<Image>()

    lateinit var size: ImageConfiguration.Size
    lateinit var type: ImageType

    private val onPhotoItemClickListener = object : PhotoItemClickListener {
        override fun onClick(data: Image) {
            image.value = data
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArgs()
        observeImage()
    }

    private fun initArgs() {
        when (args.type) {
            PhotoViewHolderType.BACKDROP -> {
                size = ImageConfiguration.Size.BACKDROP
                type = ImageType.BACKGROUND
            }
            PhotoViewHolderType.POSTER -> {
                size = ImageConfiguration.Size.POSTER
                type = ImageType.BACKGROUND
            }
            PhotoViewHolderType.PROFILE -> {
                size = ImageConfiguration.Size.PROFILE
                type = ImageType.AVATAR
            }
        }
        photoAdapter.setType(args.type)
        setupRecyclerView()
        photoAdapter.submitList(args.images.data)
        image.value = args.current
    }

    private fun setupRecyclerView() {
        photoAdapter.setListener(onPhotoItemClickListener)
        rvPhoto?.adapter = photoAdapter

        photoAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(
                positionStart: Int,
                itemCount: Int
            ) {
                rvPhoto.scrollToPosition(args.images.data.indexOf(args.current))
                photoAdapter.unregisterAdapterDataObserver(this)
            }
        })
    }

    private fun observeImage() {
        image.observe(viewLifecycleOwner, {
            imgPhoto?.load(
                it?.filePath,
                size = size,
                type = type
            )
        })
    }
}