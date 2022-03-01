package com.example.tetrainingandroid.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.data.model.*
import com.example.tetrainingandroid.databinding.PeopleFragmentBinding
import com.example.tetrainingandroid.di.CareerAsCastAdapter
import com.example.tetrainingandroid.di.CareerAsCrewAdapter
import com.example.tetrainingandroid.di.ListMovieAdapter
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.ui.detail.adapter.MovieAdapter
import com.example.tetrainingandroid.ui.media.adapter.image.PhotoAdapter
import com.example.tetrainingandroid.ui.media.adapter.image.PhotoViewHolderType
import com.example.tetrainingandroid.ui.media.adapter.model.Images
import com.example.tetrainingandroid.ui.people.adapter.career.CareerAdapter
import com.example.tetrainingandroid.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PeopleFragment: CacheViewFragment<PeopleViewModel>(R.layout.people_fragment) {
    override val viewModel: PeopleViewModel by viewModels()
    private val args: PeopleFragmentArgs by navArgs()

    @ListMovieAdapter @Inject lateinit var knownForAdapter: MovieAdapter
    @Inject lateinit var profileAdapter: PhotoAdapter
    @CareerAsCastAdapter @Inject lateinit var careerAsCastAdapter: CareerAdapter
    @CareerAsCrewAdapter @Inject lateinit var careerAsCrewAdapter: CareerAdapter

    private val profilePhotos = mutableListOf<Image>()
    private var binding: PeopleFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PeopleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        savedInstanceState?.putInt("peopleId", args.peopleId)
        observerData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        knownForAdapter.setListener(::navigateToDetailFragment)
        binding.rvKnownFor.adapter = knownForAdapter

        profileAdapter.setProfileType()
        profileAdapter.setListener { image, _ -> navigateToPhotoViewer(image) }
        binding.rvMorePhotos.adapter = profileAdapter

        binding.rvCareerAsCast.adapter = careerAsCastAdapter
        binding.rvCareerAsCrew.adapter = careerAsCrewAdapter
    }

    private fun observerData() {
        viewModel.people.observe(viewLifecycleOwner) { cast ->
            if (cast != null) {
                initView(cast)
            }
        }
    }

    private fun initView(people: People) {
        binding.collapsingToolbarLayout.title = people.name ?: getString(R.string.unknown)
        binding.llPersonalInfo.apply {
            txtKnownFor.text = people.knownForDepartment ?: getString(R.string.unknown)
            txtGender.text = people.getGenre(this@PeopleFragment)
            txtBirthday.text = people.birthday ?: getString(R.string.unknown)
            txtPlaceBirth.text = people.placeOfBirth ?: getString(R.string.unknown)
        }
        if (people.biography.isNullOrEmpty()) {
            binding.groupBiography.visibility = View.GONE
        } else {
            binding.groupBiography.visibility = View.VISIBLE
            binding.txtBiography.text = people.biography
        }
        binding.imgAvatar.load(
            people.profilePath,
            size = ImageConfiguration.Size.PROFILE,
            type = ImageType.AVATAR
        )
        setupKnownForMovie(people.movieCredits)
        setupMorePhotos(people.images?.profiles)
        setupCareer(people.career)
    }

    private fun setupKnownForMovie(movies: MovieWrapper?) {
        val knownForList = if (args.isCast) {
            movies?.cast
        } else {
            movies?.crew
        }

        if (knownForList.isNullOrEmpty()) {
            binding.groupKnownForMovie.visibility = View.GONE
        } else {
            binding.groupKnownForMovie.visibility = View.VISIBLE
            knownForAdapter.submitList(knownForList)
        }
    }

    private fun setupMorePhotos(images: List<Image>?) {
        if (images.isNullOrEmpty()) {
            binding.groupMorePhotos.visibility = View.GONE
        } else {
            profilePhotos.clear()
            profilePhotos.addAll(images)
            binding.groupMorePhotos.visibility = View.VISIBLE
            profileAdapter.submitList(images)
        }
    }

    private fun setupCareer(career: CareerWrapper?) {
        val careerAsCast = career?.cast?.filter { !it.releaseDate.isNullOrEmpty() && !it.title.isNullOrEmpty() } ?: listOf()
        if (careerAsCast.isEmpty()) {
            binding.groupCareerAsCast.visibility = View.GONE
        } else {
            binding.groupCareerAsCast.visibility = View.VISIBLE
            careerAsCastAdapter.submitList(careerAsCast)
        }
        val careerAsCrew = career?.crew?.filter { !it.releaseDate.isNullOrEmpty() && !it.title.isNullOrEmpty() } ?: listOf()
        if (careerAsCast.isEmpty()) {
            binding.groupCareerAsCrew.visibility = View.GONE
        } else {
            binding.groupCareerAsCrew.visibility = View.VISIBLE
            careerAsCrewAdapter.submitList(careerAsCrew)
        }
        binding.llPersonalInfo.apply {
            val knownForCredits = careerAsCast.size + careerAsCrew.size
            if (knownForCredits == 0) {
                groupKnownCredits.visibility = View.GONE
            } else {
                groupKnownCredits.visibility = View.VISIBLE
                txtKnownCredits.text = knownForCredits.toString()
            }
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