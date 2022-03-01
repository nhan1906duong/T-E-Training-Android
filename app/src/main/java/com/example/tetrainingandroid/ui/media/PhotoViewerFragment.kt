package com.example.tetrainingandroid.ui.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.BaseFragment
import com.example.tetrainingandroid.data.model.Image
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.databinding.PhotoViewerFragmentBinding
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.ui.media.adapter.image.PhotoAdapter
import com.example.tetrainingandroid.ui.media.adapter.image.PhotoViewHolderType
import com.example.tetrainingandroid.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotoViewerFragment : BaseFragment(R.layout.photo_viewer_fragment) {
    @Inject lateinit var photoAdapter: PhotoAdapter

    private val args: PhotoViewerFragmentArgs by navArgs()
    private var binding: PhotoViewerFragmentBinding by autoCleared()

    private val image = MutableLiveData<Image>()

    lateinit var type: ImageType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PhotoViewerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
        initArgs()
        observeImage()
    }

    private fun initEvent() {
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
            binding.toolbar.setNavigationIconTint(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }

    private fun initArgs() {
        type = when (args.type) {
            PhotoViewHolderType.BACKDROP -> {
                binding.imgPhoto.scaleType = ImageView.ScaleType.FIT_CENTER
                ImageType.BACKGROUND
            }
            PhotoViewHolderType.POSTER -> {
                ImageType.BACKGROUND
            }
            PhotoViewHolderType.PROFILE -> {
                ImageType.AVATAR
            }
        }
        photoAdapter.setType(args.type)
        setupRecyclerView()
        photoAdapter.submitList(args.images.data)
        image.value = args.current
    }

    private fun setupRecyclerView() {
        photoAdapter.setListener{ data, position ->
            binding.rvPhoto.smoothScrollToPosition(position)
            image.value = data
        }
        binding.rvPhoto.adapter = photoAdapter

        photoAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(
                positionStart: Int,
                itemCount: Int
            ) {
                binding.rvPhoto.scrollToPosition(args.images.data.indexOf(args.current))
                photoAdapter.unregisterAdapterDataObserver(this)
            }
        })
    }

    private fun observeImage() {
        image.observe(viewLifecycleOwner) {
            binding.imgPhoto.load(
                it?.filePath,
                size = ImageConfiguration.Size.ORIGINAL,
                type = type,
                removePlaceholder = true
            )
        }
    }
}