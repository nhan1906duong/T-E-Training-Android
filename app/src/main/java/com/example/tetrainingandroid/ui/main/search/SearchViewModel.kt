package com.example.tetrainingandroid.ui.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.data.model.Searchable
import com.example.tetrainingandroid.repo.SearchRepository
import com.example.tetrainingandroid.ui.shared.LoadMoreState
import com.example.tetrainingandroid.ui.shared.Page
import com.example.tetrainingandroid.ui.shared.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: SearchRepository
): BaseViewModel() {

    private val _data = MutableLiveData<PageState<Searchable>>()
    val data: LiveData<PageState<Searchable>> = _data

    private var _currentQuery: String = ""
    private val searchPage = Page<Searchable>()

    private val scope = CoroutineScope(Dispatchers.Main)

    fun search(query: String) {
        _currentQuery = query
        searchPage.refresh()
        fetch()
    }

    fun loadMore() = fetch()

    private fun fetch() {
        scope.apply {
            launch(getHandler()) {
                ensureActive()
                processRequest()
            }
        }
    }

    private suspend fun processRequest() {
        val response = repo.search(_currentQuery, searchPage.page)
        val totalPage = response.totalPages ?: 1
        if (searchPage.page == totalPage) {
            val result = response.results?.filterNotNull() ?: listOf()
            searchPage.complete(result)
            _data.value = PageState(
                data = searchPage.data,
                state = LoadMoreState.END
            )
        } else {
            val result = response.results?.filterNotNull()
            if (result.isNullOrEmpty()) {
                searchPage.add(listOf())
                processRequest() // not test this case
            } else {
                searchPage.add(result)
                _data.value = PageState(
                    data = searchPage.data,
                    state = LoadMoreState.NORMAL
                )
            }
        }
    }
}