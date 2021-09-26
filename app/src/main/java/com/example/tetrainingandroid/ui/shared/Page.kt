package com.example.tetrainingandroid.ui.shared

class Page<T> {
    private val _data = mutableListOf<T>()
    var page = 1
    private var hasNext: Boolean = true

    fun isComplete() = !hasNext

    val data: List<T> = _data

    fun refresh() {
        page = 1
        _data.clear()
    }

    fun add(list: List<T>) {
        ++page
        _data += list
    }

    fun complete(list: List<T>) {
        hasNext = false
        _data += list
    }
}

class PageState<T>(
    val data: List<T>?,
    val state: LoadMoreState = LoadMoreState.NORMAL
)

enum class LoadMoreState {
    NORMAL,
    END
}