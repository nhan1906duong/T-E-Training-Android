package com.example.tetrainingandroid.ui.main.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.ui.main.MainFragmentDirections
import com.example.tetrainingandroid.ui.main.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment: CacheViewFragment<SearchViewModel>(R.layout.search_fragment) {
    override val viewModel: SearchViewModel by viewModels()

    @Inject lateinit var searchAdapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAdapter.setListener { data ->
            when(data) {
                is Movie -> navigateToMovieDetail(data)
                is People -> navigateToPeopleDetail(data)
                else -> {}
            }
        }
        rvSearch?.adapter = searchAdapter
        viewModel.data.observe(viewLifecycleOwner, {
            searchAdapter.submitList(it?.filterNotNull())
        })
        viewModel.search("X-men")
    }

    private fun navigateToMovieDetail(movie: Movie) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movie.id!!)
        findNavController().navigate(action)
    }

    private fun navigateToPeopleDetail(people: People) {
        val action = MainFragmentDirections.actionMainFragmentToCastFragment(people.id!!, true)
        findNavController().navigate(action)
    }
}