package com.example.tetrainingandroid.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detail_header_layout.*

@AndroidEntryPoint
class DetailFragment: CacheViewFragment(R.layout.detail_fragment) {
    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        observeMovie()
    }

    private fun observeMovie() {
        viewModel.movie.observe(viewLifecycleOwner, { movie ->
            if (movie != null) {
                imgBackdrop?.load(movie.backdropPath, type = ImageConfiguration.Size.BACKDROP)
                imgPoster?.load(movie.posterPath, type = ImageConfiguration.Size.POSTER)
                txtTitle?.text = movie.title ?: ""
            }
        })
        viewModel.error.observe(viewLifecycleOwner, {message -> toast(message)})
    }
}