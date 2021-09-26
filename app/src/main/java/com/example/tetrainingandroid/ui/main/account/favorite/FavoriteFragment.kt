package com.example.tetrainingandroid.ui.main.account.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.di.FavoriteMovieAdapter
import com.example.tetrainingandroid.extensions.toast
import com.example.tetrainingandroid.ui.detail.adapter.MovieAdapter
import com.example.tetrainingandroid.ui.main.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.favorite_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment: CacheViewFragment<FavoriteViewModel>(R.layout.favorite_fragment) {
    override val viewModel: FavoriteViewModel by viewModels()

    private val userViewModel: UserViewModel by activityViewModels()

    @FavoriteMovieAdapter
    @Inject lateinit var adapter: MovieAdapter

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            adapter.submitList(it.data)
        })
        adapter.setListener(::navigateToDetail)
        adapter.setChildItemListener(::removeFromFavorite)
        rvFavorite?.adapter = adapter
    }

    private fun navigateToDetail(movie: Movie) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(movie.id!!)
        findNavController().navigate(action)
    }

    private fun removeFromFavorite(movie: Movie) {
        userViewModel.removeFromFavorite(movie.id!!).observe(viewLifecycleOwner, {
            toast(it?.statusMessage)
            if (it?.success == true) {
                viewModel.loadData()
            }
        })
    }
}