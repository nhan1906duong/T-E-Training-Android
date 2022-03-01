package com.example.tetrainingandroid.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.LoadingDataFragment
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.databinding.SearchFragmentBinding
import com.example.tetrainingandroid.ui.main.MainFragmentDirections
import com.example.tetrainingandroid.ui.main.search.adapter.SearchAdapter
import com.example.tetrainingandroid.ui.shared.LoadMoreState
import com.example.tetrainingandroid.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment: LoadingDataFragment<SearchViewModel>(R.layout.search_fragment) {
    override val viewModel: SearchViewModel by viewModels()

    @Inject lateinit var searchAdapter: SearchAdapter
    private var binding: SearchFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupAdapter() {
        searchAdapter.setListener { data ->
            when (data) {
                is Movie -> navigateToMovieDetail(data)
                is People -> navigateToPeopleDetail(data)
                else -> {
                }
            }
        }
        searchAdapter.setLoadMoreListener {
            viewModel.loadMore()
        }
        binding.rvSearch.adapter = searchAdapter
    }

    private fun observerSearch() {
        viewModel.data.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            if (it.state == LoadMoreState.END) searchAdapter.setEndLoading()
            searchAdapter.setList(it.data)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observerSearch()
        initEvent()
    }

    private fun initEvent() {
        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
            }
            true
        }
        binding.imgSearch.setOnClickListener { search() }
        binding.rootLayout.setOnClickListener { hideKeyboard() }
    }

    private fun navigateToMovieDetail(movie: Movie) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movie.id!!)
        findNavController().navigate(action)
    }

    private fun navigateToPeopleDetail(people: People) {
        val action = MainFragmentDirections.actionMainFragmentToCastFragment(people.id!!, true)
        findNavController().navigate(action)
    }

    private fun search() {
        hideKeyboard()
        val query = binding.edtSearch.text?.toString()
        if (!query.isNullOrEmpty()) {
            viewModel.search(query)
        }
        searchAdapter.resetLoading()
    }
}