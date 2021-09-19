package com.example.tetrainingandroid.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.ui.main.MainFragmentDirections
import com.example.tetrainingandroid.ui.main.home.adapter.MovieAdapter
import com.example.tetrainingandroid.ui.main.home.adapter.MovieItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.now_playing_movies_layout.*
import kotlinx.android.synthetic.main.popular_movies_layout.*
import kotlinx.android.synthetic.main.top_rated_movies_layout.*
import kotlinx.android.synthetic.main.up_coming_movies_layout.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : CacheViewFragment(R.layout.home_fragment) {

    private val viewModel: HomeViewModel by viewModels()

    @Inject lateinit var popularMoviesAdapter: MovieAdapter
    @Inject lateinit var topRatedMoviesAdapter: MovieAdapter
    @Inject lateinit var nowPlayingMoviesAdapter: MovieAdapter
    @Inject lateinit var upComingMoviesAdapter: MovieAdapter

    private val onItemClickListener = MovieItemClickListener { movieId ->
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movieId)
        parentFragment?.findNavController()?.navigate(action)
    }

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        setupRecyclerView()
        observeData()
    }

    private fun observeData() {
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
    }

    private fun setupRecyclerView() {
        popularMoviesAdapter.setListener(onItemClickListener)
        rvPopularMovies?.adapter = popularMoviesAdapter

        topRatedMoviesAdapter.setListener(onItemClickListener)
        rvTopRatedMovies?.adapter = topRatedMoviesAdapter

        nowPlayingMoviesAdapter.setListener(onItemClickListener)
        rvNowPlayingMovies?.adapter = nowPlayingMoviesAdapter

        upComingMoviesAdapter.setListener(onItemClickListener)
        rvUpComingMovies?.adapter = upComingMoviesAdapter
    }
}