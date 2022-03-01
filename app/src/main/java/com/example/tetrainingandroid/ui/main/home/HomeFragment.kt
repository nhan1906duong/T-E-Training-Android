package com.example.tetrainingandroid.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.tetrainingandroid.databinding.HomeFragmentBinding
import com.example.tetrainingandroid.di.ListMovieAdapter
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.getScreenWidth
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.ui.detail.adapter.MovieAdapter
import com.example.tetrainingandroid.ui.main.MainFragmentDirections
import com.example.tetrainingandroid.ui.main.UserViewModel
import com.example.tetrainingandroid.ui.main.home.adapter.TrendingMovieAdapter
import com.example.tetrainingandroid.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
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
    private var binding: HomeFragmentBinding by autoCleared()

    private var sliderDelayJob: Job? = null
    private val sliderScope = CoroutineScope(Dispatchers.Main)
    private var isLayoutVisibleOnce: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

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
        binding.vpSlider.offscreenPageLimit = 3
        trendingMoviesAdapter.setListener(::navigateToDetailFragment)
        binding.vpSlider.adapter = trendingMoviesAdapter
    }

    private fun setViewPagerDimensionRation() {
        activity?.run {
            val screenWidth = getScreenWidth() ?: return@run
            val padding = resources.getDimension(R.dimen.slider_horizontal_padding)
            val ratio = (BACKDROP_RATIO * screenWidth) / (screenWidth - (padding * 2))
            val layoutParam = binding.vpSlider.layoutParams as? ConstraintLayout.LayoutParams
            layoutParam?.dimensionRatio = "H,$ratio:1"
            binding.vpSlider.layoutParams = layoutParam
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
        binding.vpSlider.setPageTransformer(transformer)
    }

    private fun makeViewPagerSlide() {
        binding.vpSlider.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderDelayJob?.cancel()
                sliderDelayJob = sliderScope.launch {
                    delay(SLIDER_INTERVAL)
                    binding.vpSlider.currentItem = (binding.vpSlider.currentItem + 1) % trendingMoviesAdapter.itemCount
                }
            }
        })
    }

    private fun observeData() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
            if (it == false) {
                if (!isLayoutVisibleOnce) {
                    isLayoutVisibleOnce = true
                    binding.vpSlider.visibility = View.VISIBLE
                    binding.popular.popularBarrier.visibility = View.VISIBLE
                    binding.topRated.topRatedBarrier.visibility = View.VISIBLE
                    binding.nowPlayingGroup.nowPlayingBarrier.visibility = View.VISIBLE
                    binding.upComing.upComingBarier.visibility = View.VISIBLE
                    setViewPagerDimensionRation()
                }
            }
        }

        viewModel.trendingMovies.observe(viewLifecycleOwner) {
            trendingMoviesAdapter.submitList(it)
        }

        viewModel.popularMovies.observe(viewLifecycleOwner) {
            popularMoviesAdapter.submitList(it)
        }

        viewModel.topRatedMovies.observe(viewLifecycleOwner) {
            topRatedMoviesAdapter.submitList(it)
        }

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) {
            nowPlayingMoviesAdapter.submitList(it)
        }

        viewModel.upComingMovies.observe(viewLifecycleOwner) {
            upComingMoviesAdapter.submitList(it)
        }

        userViewModel.user.observe(viewLifecycleOwner) {
            binding.headerCard.imgAvatar.load(
                it?.avatar?.getAvatarPath(),
                size = ImageConfiguration.Size.PROFILE,
                type = ImageType.AVATAR
            )
        }
    }

    private fun setupRecyclerView() {
        popularMoviesAdapter.setListener(::navigateToDetailFragment)
        binding.popular.rvPopularMovies.adapter = popularMoviesAdapter

        topRatedMoviesAdapter.setListener(::navigateToDetailFragment)
        binding.topRated.rvTopRatedMovies.adapter = topRatedMoviesAdapter

        nowPlayingMoviesAdapter.setListener(::navigateToDetailFragment)
        binding.nowPlayingGroup.rvNowPlayingMovies.adapter = nowPlayingMoviesAdapter

        upComingMoviesAdapter.setListener(::navigateToDetailFragment)
        binding.upComing.rvUpComingMovies.adapter = upComingMoviesAdapter
    }

    private fun initEvent() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }
    }

    private fun navigateToDetailFragment(movie: Movie) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movie.id!!)
        parentFragment?.findNavController()?.navigate(action)
    }
}