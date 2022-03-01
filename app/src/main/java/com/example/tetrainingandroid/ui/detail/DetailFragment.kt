package com.example.tetrainingandroid.ui.detail

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.data.model.Image
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.Youtube
import com.example.tetrainingandroid.databinding.DetailFragmentBinding
import com.example.tetrainingandroid.di.CastAdapter
import com.example.tetrainingandroid.di.CrewAdapter
import com.example.tetrainingandroid.di.ListMovieAdapter
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.extensions.toast
import com.example.tetrainingandroid.ui.detail.adapter.MovieAdapter
import com.example.tetrainingandroid.ui.people.adapter.PeopleAdapter
import com.example.tetrainingandroid.ui.genre.adapter.GenreAdapter
import com.example.tetrainingandroid.ui.main.UserViewModel
import com.example.tetrainingandroid.ui.media.adapter.image.PhotoAdapter
import com.example.tetrainingandroid.ui.media.adapter.image.PhotoViewHolderType
import com.example.tetrainingandroid.ui.media.adapter.model.Images
import com.example.tetrainingandroid.ui.media.adapter.video.YoutubeAdapter
import com.example.tetrainingandroid.ui.review.ReviewAdapter
import com.example.tetrainingandroid.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : CacheViewFragment<DetailViewModel>(R.layout.detail_fragment) {
    override val viewModel: DetailViewModel by viewModels()

    private val userViewModel: UserViewModel by activityViewModels()
    private var binding: DetailFragmentBinding by autoCleared()


    @CastAdapter @Inject lateinit var peopleAdapter: PeopleAdapter

    @CrewAdapter @Inject lateinit var crewAdapter: PeopleAdapter

    @Inject lateinit var genreAdapter: GenreAdapter
    @Inject lateinit var backdropAdapter: PhotoAdapter
    @Inject lateinit var posterAdapter: PhotoAdapter
    @Inject lateinit var youtubeAdapter: YoutubeAdapter
    @ListMovieAdapter @Inject lateinit var similarAdapter: MovieAdapter
    @Inject lateinit var reviewAdapter: ReviewAdapter

    private val args: DetailFragmentArgs by navArgs()
    private val backdropPhotos = mutableListOf<Image>()
    private val posterPhotos = mutableListOf<Image>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        val movieId = args.movieId
        savedInstanceState?.putInt("movieId", movieId)
        initView()
        observeMovie()
    }

    private fun initView() {
        initSwipeRefreshEvent()

        binding.rootLayout.setOnClickListener { hideKeyboard() }

        binding.detail.ratingLayout.btnSend.setOnClickListener { sendReview() }
        binding.detail.apply {
            rvGenre.adapter = genreAdapter

            peopleAdapter.setListener { people -> navigateToPeopleFragment(people.id!!, isCast = true) }
            rvCast.adapter = peopleAdapter

            crewAdapter.setListener { people -> navigateToPeopleFragment(people.id!!, isCast = false)}
            rvCrew.adapter = crewAdapter

            youtubeAdapter.setListener(::navigateToYoutubePlayer)
            rvTrailer.adapter = youtubeAdapter

            binding.header.imgBackdrop.setOnClickListener { backdropPhotos.firstOrNull()?.let { navigateToPhotoViewer(it) } }

            backdropAdapter.setListener { image, _ -> navigateToPhotoViewer(image) }
            rvBackdrop.adapter = backdropAdapter

            binding.header.imgPoster.setOnClickListener { posterPhotos.firstOrNull()?.let { navigateToPhotoViewer(it, isPoster = true) } }

            posterAdapter.setPosterType()
            posterAdapter.setListener { image, _ -> navigateToPhotoViewer(image, isPoster = true) }
            rvPoster.adapter = posterAdapter

            similarAdapter.setListener(::navigateToDetailFragment)
            rvRelativeMovie.adapter = similarAdapter

            rvReview.adapter = reviewAdapter
        }

        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
    }

    private fun initSwipeRefreshEvent() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }
    }

    private fun observeMovie() {
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                binding.collapsingToolbarLayout.title = movie.title ?: ""
                binding.header.apply {
                    imgBackdrop.load(
                        movie.backdropPath,
                        size = ImageConfiguration.Size.BACKDROP,
                        type = ImageType.BACKGROUND
                    )
                    imgPoster.load(
                        movie.posterPath,
                        size = ImageConfiguration.Size.POSTER,
                        type = ImageType.BACKGROUND
                    )
                    txtTitle.text = movie.title ?: ""
                    txtReleaseDate.text = movie.releaseDate ?: ""
                    txtRating.text = String.format("%.1f", movie.voteAverage ?: 0.0f)
                }
                binding.detail.apply {
                    if (movie.tagline.isNullOrEmpty()) {
                        txtTagLine.visibility = View.GONE
                    } else {
                        txtTagLine.text = movie.tagline
                    }

                    if (movie.overview.isNullOrEmpty()) {
                        groupOverview.visibility = View.GONE
                    } else {
                        txtOverview.text = movie.overview
                    }

                    if (movie.genres.isNullOrEmpty()) {
                        rvGenre.visibility = View.GONE
                    } else {
                        genreAdapter.submitList(movie.genres)
                    }

                    if (movie.casts?.cast.isNullOrEmpty()) {
                        groupCast.visibility = View.GONE
                    } else {
                        peopleAdapter.submitList(movie.casts?.cast)
                    }

                    if (movie.casts?.crew.isNullOrEmpty()) {
                        groupCrew.visibility = View.GONE
                    } else {
                        crewAdapter.submitList(movie.casts?.crew?.take(5))
                    }

                    if (movie.images?.backdrops.isNullOrEmpty()) {
                        groupBackdrop.visibility = View.GONE
                    } else {
                        backdropPhotos.clear()
                        backdropPhotos.addAll(movie.images?.backdrops!!)
                        backdropAdapter.submitList(movie.images.backdrops)
                    }

                    if (movie.images?.posters.isNullOrEmpty()) {
                        groupPoster.visibility = View.GONE
                    } else {
                        posterPhotos.clear()
                        posterPhotos.addAll(movie.images?.posters!!)
                        posterAdapter.submitList(movie.images.posters)
                    }

                    if (movie.videos?.results.isNullOrEmpty()) {
                        groupTrailer.visibility = View.GONE
                    } else {
                        youtubeAdapter.submitList(movie.videos?.results)
                    }

                    if (movie.similar?.results.isNullOrEmpty()) {
                        groupRelateMovies.visibility = View.GONE
                    } else {
                        similarAdapter.submitList(movie.similar?.results)
                    }

                    if (movie.reviews?.results.isNullOrEmpty()) {
                        groupReview.visibility = View.GONE
                    } else {
                        reviewAdapter.submitList(movie.reviews?.results?.take(3))
                    }
                }
            }
        }
    }

    private fun navigateToPhotoViewer(image: Image, isPoster: Boolean = false) {
        val action = DetailFragmentDirections.actionDetailFragmentToPhotoViewerFragment(
            images = Images(if (isPoster) posterPhotos else backdropPhotos),
            type = if (isPoster) PhotoViewHolderType.POSTER else PhotoViewHolderType.BACKDROP,
            current = image
        )
        findNavController().navigate(action)
    }

    private fun navigateToYoutubePlayer(youtube: Youtube) {
        val action =
            DetailFragmentDirections.actionDetailFragmentToYoutubeFragment(youtube, args.movieId)
        findNavController().navigate(action)
    }

    private fun navigateToPeopleFragment(peopleId: Int, isCast: Boolean) {
        val action =
            DetailFragmentDirections.actionDetailFragmentToPeopleFragment(peopleId, isCast)
        findNavController().navigate(action)
    }

    private fun navigateToDetailFragment(movie: Movie) {
        val action = DetailFragmentDirections.actionDetailFragmentSelf(movie.id!!)
        findNavController().navigate(action)
    }

    private fun sendReview() {
        showLoading()
        val rating = binding.detail.ratingLayout.ratingBar.rating
        val content = binding.detail.ratingLayout.edtContent.text?.toString()
        userViewModel.postComment(args.movieId, rating, content).observe(viewLifecycleOwner) {
            it?.let { hideLoading() }
            toast(it?.statusMessage)
        }
    }

    override fun onLoadingDataError(message: String?) {
        super.onLoadingDataError(message)
        hideLoading()
    }

    private fun showLoading() = View.VISIBLE.also { binding.linearProgress.visibility = it }
    private fun hideLoading() = View.GONE.also { binding.linearProgress.visibility = it }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favorite -> {
                showLoading()
                userViewModel.markFavorite(args.movieId).observe(viewLifecycleOwner) {
                    hideLoading()
                    toast(it?.statusMessage)
                }
            }
            R.id.watchlist -> {
                showLoading()
                userViewModel.addToWatchList(args.movieId).observe(viewLifecycleOwner) {
                    hideLoading()
                    toast(it?.statusMessage)
                }
            }
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}