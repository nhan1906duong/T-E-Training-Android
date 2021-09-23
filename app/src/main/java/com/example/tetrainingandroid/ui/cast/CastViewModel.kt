package com.example.tetrainingandroid.ui.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.repo.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: PeopleRepository
): BaseViewModel() {
    private val castId: Int = savedStateHandle["castId"] ?: throw IllegalArgumentException("Missing cast id")
    private val _people = MutableLiveData<People>()

    val people = _people as LiveData<People>

    override fun loadData() {
        super.loadData()
        viewModelScope.launch(getHandler()){
            val result = (async { repo.getPeople(castId) }).await()
            _people.value = result
        }
    }
}