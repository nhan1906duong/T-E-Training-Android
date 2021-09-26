package com.example.tetrainingandroid.ui.main.account.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.Searchable
import com.example.tetrainingandroid.repo.UserRepository
import com.example.tetrainingandroid.ui.shared.LoadMoreState
import com.example.tetrainingandroid.ui.shared.Page
import com.example.tetrainingandroid.ui.shared.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repo: UserRepository
): BaseViewModel() {
    private val _data = MutableLiveData<PageState<Movie>>()
    val data: LiveData<PageState<Movie>> = _data

    private val pageData = Page<Movie>()

    override fun loadData() {
        super.loadData()
        refresh()
    }

    fun refresh() {
        pageData.refresh()
        getFavorite()
    }

    fun loadMore() = getFavorite()

    private fun getFavorite() {
        viewModelScope.launch(getHandler()) {
            val response = repo.getFavorite(pageData.page)
            val totalPage = response?.totalPages ?: 1
            if (pageData.page == totalPage) {
                val result = response?.results ?: listOf()
                pageData.complete(result)
                _data.value = PageState(
                    data = pageData.data,
                    state = LoadMoreState.END
                )
            } else {
                val result = response?.results
                if (result.isNullOrEmpty()) {
                    pageData.add(listOf())
                    getFavorite() // not test this case
                } else {
                    pageData.add(result)
                    _data.value = PageState(
                        data = pageData.data,
                        state = LoadMoreState.NORMAL
                    )
                }
            }
        }
    }
}