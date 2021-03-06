package com.example.tetrainingandroid.ui.main.account.watchlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.di.WatchlistMovieAdapter
import com.example.tetrainingandroid.extensions.toast
import com.example.tetrainingandroid.ui.detail.adapter.MovieAdapter
import com.example.tetrainingandroid.ui.main.UserViewModel
import com.example.tetrainingandroid.ui.main.account.favorite.FavoriteFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.watchlist_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class WatchlistFragment: CacheViewFragment<WatchlistViewModel>(R.layout.watchlist_fragment) {
    override val viewModel: WatchlistViewModel by viewModels()

    private val userViewModel: UserViewModel by activityViewModels()

    @WatchlistMovieAdapter
    @Inject lateinit var adapter: MovieAdapter

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            adapter.submitList(it.data)
        })
        adapter.setListener(::navigateToDetail)
        adapter.setChildItemListener(::removeFromWatchlist)
        rvWatchlist?.adapter = adapter
    }

    private fun navigateToDetail(movie: Movie) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(movie.id!!)
        findNavController().navigate(action)
    }

    private fun removeFromWatchlist(movie: Movie) {
        userViewModel.removeFromWatchlist(movie.id!!).observe(viewLifecycleOwner, {
            toast(it?.statusMessage)
            if (it?.success == true) {
                viewModel.loadData()
            }
        })
    }
}