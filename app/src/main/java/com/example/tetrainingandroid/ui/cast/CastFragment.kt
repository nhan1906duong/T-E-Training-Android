package com.example.tetrainingandroid.ui.cast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.ui.main.home.adapter.MovieAdapter
import com.example.tetrainingandroid.ui.main.home.adapter.MovieItemClickListener
import com.example.tetrainingandroid.ui.media.adapter.image.ProfileAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.cast_fragment.*
import kotlinx.android.synthetic.main.cast_fragment.collapsingToolbarLayout
import kotlinx.android.synthetic.main.personal_info_layout.*
import javax.inject.Inject

@AndroidEntryPoint
class CastFragment: CacheViewFragment<CastViewModel>(R.layout.cast_fragment) {
    override val viewModel: CastViewModel by viewModels()
    private val args: CastFragmentArgs by navArgs()

    @Inject lateinit var knownForAdapter: MovieAdapter
    @Inject lateinit var profileAdapter: ProfileAdapter

    private val onMovieItemClickListener = MovieItemClickListener {movieId ->
        val action = CastFragmentDirections.actionCastFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        savedInstanceState?.putInt("castId", args.castId)
        observerData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        knownForAdapter.setListener(onMovieItemClickListener)
        rvKnownFor?.adapter = knownForAdapter

        rvMorePhotos?.adapter = profileAdapter
    }

    private fun observerData() {
        viewModel.people.observe(viewLifecycleOwner, { cast ->
            if (cast != null) {
                initView(cast)
            }
        })
    }

    private fun initView(cast: People) {
        collapsingToolbarLayout?.title = cast.name ?: getString(R.string.unknown)
        //txtName?.text = cast.name ?: getString(R.string.unknown)
        txtKnownFor?.text = cast.knownForDepartment ?: getString(R.string.unknown)
        txtKnownCredits?.text = "18"
        txtGender?.text = cast.getGenre(this)
        txtBirthday?.text = cast.birthday ?: getString(R.string.unknown)
        txtPlaceBirth?.text = cast.placeOfBirth ?: getString(R.string.unknown)
        if (cast.biography.isNullOrEmpty()) {
            groupBiography?.visibility = View.GONE
        } else {
            groupBiography?.visibility = View.VISIBLE
            txtBiography?.text = cast.biography
        }

        imgAvatar?.load(
            cast.profilePath,
            size = ImageConfiguration.Size.PROFILE,
            type = ImageType.AVATAR
        )
        setupKnownForMovie(cast)
        setupMorePhotos(cast)
    }

    private fun setupKnownForMovie(cast: People) {
        val knownForList = if (args.isCast) {
            cast.movieCredits?.cast
        } else {
            cast.movieCredits?.crew
        }

        if (knownForList.isNullOrEmpty()) {
            groupKnownForMovie?.visibility = View.GONE
        } else {
            groupKnownForMovie?.visibility = View.VISIBLE
            knownForAdapter.submitList(knownForList)
        }
    }

    private fun setupMorePhotos(cast: People) {
        val images = cast.images?.profiles
        if (images.isNullOrEmpty()) {
            groupMorePhotos?.visibility = View.GONE
        } else {
            groupMorePhotos?.visibility = View.VISIBLE
            profileAdapter.submitList(images)
        }
    }

}