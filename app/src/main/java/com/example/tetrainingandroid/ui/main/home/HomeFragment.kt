package com.example.tetrainingandroid.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.di.ListMovieAdapter
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.getScreenWidth
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.ui.detail.adapter.MovieAdapter
import com.example.tetrainingandroid.ui.main.MainFragmentDirections
import com.example.tetrainingandroid.ui.main.UserViewModel
import com.example.tetrainingandroid.ui.main.home.adapter.TrendingMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header_home_layout.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.now_playing_movies_layout.*
import kotlinx.android.synthetic.main.popular_movies_layout.*
import kotlinx.android.synthetic.main.top_rated_movies_layout.*
import kotlinx.android.synthetic.main.up_coming_movies_layout.*
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : CacheViewFragment<HomeViewModel>(R.layout.home_fragment) {
    companion object {
        private const val SLIDER_INTERVAL = 2000L //millisecond
        private const val BACKDROP_RATIO = 300.0f / 169
    }

    @Inject lateinit var trendingMoviesAdapter: TrendingMovieAdapter
    @ListMovieAdapter @Inject lateinit var popularMoviesAdapter: MovieAdapter
    @ListMovieAdapter @Inject lateinit var topRatedMoviesAdapter: MovieAdapter
    @ListMovieAdapter @Inject lateinit var nowPlayingMoviesAdapter: MovieAdapter
    @ListMovieAdapter @Inject lateinit var upComingMoviesAdapter: MovieAdapter

    override val viewModel: HomeViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    private var sliderDelayJob: Job? = null
    private val sliderScope = CoroutineScope(Dispatchers.Main)
    private var isLayoutVisibleOnce: Boolean = false

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        observeData()
        initView()
        initEvent()
    }

    private fun initView() {
        setupTrendingSlider()
        setupRecyclerView()
    }

    private fun setupTrendingSlider() {
        setViewPagerAnimation()
        makeViewPagerSlide()
        vpSlider?.offscreenPageLimit = 3
        trendingMoviesAdapter.setListener(::navigateToDetailFragment)
        vpSlider?.adapter = trendingMoviesAdapter
    }

    private fun setViewPagerDimensionRation() {
        activity?.run {
            val screenWidth = getScreenWidth() ?: return@run
            val padding = resources.getDimension(R.dimen.slider_horizontal_padding)
            val ratio = (BACKDROP_RATIO * screenWidth) / (screenWidth - (padding * 2))
            val layoutParam = vpSlider?.layoutParams as? ConstraintLayout.LayoutParams
            layoutParam?.dimensionRatio = "H,$ratio:1"
            vpSlider?.layoutParams = layoutParam
        }
    }

    private fun setViewPagerAnimation() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(resources.getDimension(R.dimen.layout_margin_16dp).toInt()))
        // Make other page smaller
        transformer.addTransformer { page, position ->
            val radian = 1 - abs(position)
            page.scaleY = 0.85f + radian * 0.15f
        }
        vpSlider?.setPageTransformer(transformer)
    }

    private fun makeViewPagerSlide() {
        vpSlider?.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderDelayJob?.cancel()
                sliderDelayJob = sliderScope.launch {
                    delay(SLIDER_INTERVAL)
                    vpSlider?.currentItem = (vpSlider.currentItem + 1) % trendingMoviesAdapter.itemCount
                }
            }
        })
    }

    private fun observeData() {
        viewModel.loading.observe(viewLifecycleOwner, {
            if (it == true) {
                pbLoading?.visibility = View.VISIBLE
            } else {
                pbLoading?.visibility = View.GONE
            }
            if (it == false) {
                if (!isLayoutVisibleOnce) {
                    isLayoutVisibleOnce = true
                    vpSlider?.visibility = View.VISIBLE
                    popularBarrier?.visibility = View.VISIBLE
                    topRatedBarrier?.visibility = View.VISIBLE
                    nowPlayingBarrier?.visibility = View.VISIBLE
                    upComingBarier?.visibility = View.VISIBLE
                    setViewPagerDimensionRation()
                }
            }
        })

        viewModel.trendingMovies.observe(viewLifecycleOwner, {
            trendingMoviesAdapter.submitList(it)
        })

        viewModel.popularMovies.observe(viewLifecycleOwner, {
            popularMoviesAdapter.submitList(it)
        })

        viewModel.topRatedMovies.observe(viewLifecycleOwner, {
            topRatedMoviesAdapter.submitList(it)
        })

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, {
            nowPlayingMoviesAdapter.submitList(it)
        })

        viewModel.upComingMovies.observe(viewLifecycleOwner, {
            upComingMoviesAdapter.submitList(it)
        })

        userViewModel.user.observe(viewLifecycleOwner, {
            imgAvatar.load(
                it?.avatar?.getAvatarPath(),
                size = ImageConfiguration.Size.PROFILE,
                type = ImageType.AVATAR
            )
        })
    }

    private fun setupRecyclerView() {
        popularMoviesAdapter.setListener(::navigateToDetailFragment)
        rvPopularMovies?.adapter = popularMoviesAdapter

        topRatedMoviesAdapter.setListener(::navigateToDetailFragment)
        rvTopRatedMovies?.adapter = topRatedMoviesAdapter

        nowPlayingMoviesAdapter.setListener(::navigateToDetailFragment)
        rvNowPlayingMovies?.adapter = nowPlayingMoviesAdapter

        upComingMoviesAdapter.setListener(::navigateToDetailFragment)
        rvUpComingMovies?.adapter = upComingMoviesAdapter
    }

    private fun initEvent() {
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
            viewModel.refresh()
        }
    }

    private fun navigateToDetailFragment(movie: Movie) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movie.id!!)
        parentFragment?.findNavController()?.navigate(action)
    }
}