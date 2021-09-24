package com.example.tetrainingandroid.ui.people

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.data.model.Image
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.ui.main.home.adapter.MovieAdapter
import com.example.tetrainingandroid.ui.main.home.adapter.MovieItemClickListener
import com.example.tetrainingandroid.ui.media.adapter.image.PhotoAdapter
import com.example.tetrainingandroid.ui.media.adapter.image.PhotoViewHolderType
import com.example.tetrainingandroid.ui.media.adapter.model.Images
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.people_fragment.*
import kotlinx.android.synthetic.main.people_fragment.collapsingToolbarLayout
import kotlinx.android.synthetic.main.personal_info_layout.*
import javax.inject.Inject

@AndroidEntryPoint
class PeopleFragment: CacheViewFragment<PeopleViewModel>(R.layout.people_fragment) {
    override val viewModel: PeopleViewModel by viewModels()
    private val args: PeopleFragmentArgs by navArgs()

    @Inject lateinit var knownForAdapter: MovieAdapter
    @Inject lateinit var profileAdapter: PhotoAdapter
    private val profilePhotos = mutableListOf<Image>()

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        savedInstanceState?.putInt("peopleId", args.peopleId)
        observerData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        knownForAdapter.setListener(::navigateToDetailFragment)
        rvKnownFor?.adapter = knownForAdapter

        profileAdapter.setProfileType()
        profileAdapter.setListener { image, _ -> navigateToPhotoViewer(image) }
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
            profilePhotos.clear()
            profilePhotos.addAll(images)
            groupMorePhotos?.visibility = View.VISIBLE
            profileAdapter.submitList(images)
        }
    }

    private fun navigateToPhotoViewer(image: Image) {
        val action = PeopleFragmentDirections.actionCastFragmentToPhotoViewerFragment(
            images = Images(profilePhotos),
            type = PhotoViewHolderType.PROFILE,
            current = image
        )
        findNavController().navigate(action)
    }

    private fun navigateToDetailFragment(movie: Movie) {
        val action = PeopleFragmentDirections.actionCastFragmentToDetailFragment(movie.id!!)
        findNavController().navigate(action)
    }
}