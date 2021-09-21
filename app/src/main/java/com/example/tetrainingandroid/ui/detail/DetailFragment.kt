package com.example.tetrainingandroid.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.extensions.toast
import com.example.tetrainingandroid.ui.cast.adapter.CastAdapter
import com.example.tetrainingandroid.ui.crew.adapter.CrewAdapter
import com.example.tetrainingandroid.ui.genre.adapter.GenreAdapter
import com.example.tetrainingandroid.ui.main.home.adapter.MovieAdapter
import com.example.tetrainingandroid.ui.media.adapter.image.BackdropAdapter
import com.example.tetrainingandroid.ui.media.adapter.video.YoutubeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.detail_header_layout.*
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : CacheViewFragment(R.layout.detail_fragment) {
    private val viewModel: DetailViewModel by viewModels()

    @Inject lateinit var genreAdapter: GenreAdapter
    @Inject lateinit var castAdapter: CastAdapter
    @Inject lateinit var crewAdapter: CrewAdapter
    @Inject lateinit var backdropAdapter: BackdropAdapter
    @Inject lateinit var youtubeAdapter: YoutubeAdapter
    @Inject lateinit var similarAdapter: MovieAdapter

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        val movieId = args.movieId
        viewModel.setMovie(movieId)
        observeMovie()
        initView()
    }

    private fun initView() {
        rvGenre?.adapter = genreAdapter
        rvCast?.adapter = castAdapter
        rvCrew?.adapter = crewAdapter
        rvTrailer?.adapter = youtubeAdapter
        rvPhoto?.adapter = backdropAdapter
        rvRelativeMovie?.adapter = similarAdapter
    }

    private fun observeMovie() {
        viewModel.movie.observe(viewLifecycleOwner, { movie ->
            if (movie != null) {
                imgBackdrop?.load(
                    movie.backdropPath,
                    size = ImageConfiguration.Size.BACKDROP,
                    type = ImageType.BACKGROUND
                )
                imgPoster?.load(
                    movie.posterPath,
                    size = ImageConfiguration.Size.POSTER,
                    type = ImageType.BACKGROUND
                )
                txtTitle?.text = movie.title ?: ""
                txtReleaseDate?.text = movie.releaseDate ?: ""
                txtRating?.text = String.format("%.1f", movie.voteAverage ?: 0.0f)

                if (movie.tagline.isNullOrEmpty()) {
                    txtTagLine?.visibility = View.GONE
                } else {
                    txtTagLine?.text = movie.tagline
                }

                if (movie.overview.isNullOrEmpty()) {
                    groupOverview?.visibility = View.GONE
                } else {
                    txtOverview?.text = movie.overview
                }

                if (movie.genres.isNullOrEmpty()) {
                    rvGenre?.visibility = View.GONE
                } else {
                    genreAdapter.submitList(movie.genres)
                }

                if (movie.casts?.cast.isNullOrEmpty()) {
                    groupCast?.visibility = View.GONE
                } else {
                    castAdapter.submitList(movie.casts?.cast)
                }

                if (movie.casts?.crew.isNullOrEmpty()) {
                    groupCrew?.visibility = View.GONE
                } else {
                    crewAdapter.submitList(movie.casts?.crew?.take(5))
                }

                if (movie.images?.backdrops.isNullOrEmpty()) {
                    groupPhoto?.visibility = View.GONE
                } else {
                    backdropAdapter.submitList(movie.images?.backdrops)
                }

                if (movie.videos?.results.isNullOrEmpty()) {
                    groupTrailer?.visibility = View.GONE
                } else {
                    youtubeAdapter.submitList(movie.videos?.results)
                }

                if (movie.similar?.results.isNullOrEmpty()) {
                    groupRelateMovies?.visibility = View.GONE
                } else {
                    similarAdapter.submitList(movie.similar?.results)
                }
            }
        })

        toolbar?.setNavigationIcon(R.drawable.ic_back)
        toolbar?.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        viewModel.error.observe(viewLifecycleOwner, { message -> toast(message) })
    }
}