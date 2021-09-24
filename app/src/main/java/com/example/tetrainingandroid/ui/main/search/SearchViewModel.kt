package com.example.tetrainingandroid.ui.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.data.model.Searchable
import com.example.tetrainingandroid.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: SearchRepository
): BaseViewModel() {

    private val _data = MutableLiveData<List<Searchable?>>()
    val data: LiveData<List<Searchable?>> = _data

    fun search(query: String) {
        viewModelScope.launch(getHandler()) {
            val result = (async { repo.search(query) }).await()
            _data.value = result
        }
    }
}