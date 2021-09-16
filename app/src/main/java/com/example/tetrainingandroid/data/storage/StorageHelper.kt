package com.example.tetrainingandroid.data.storage

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageHelper @Inject constructor(
    private val requestStorage: RequestTokenResponseStorage,
    private val sessionStorage: SessionStorage,
){
    fun removeAll() {
        requestStorage.remove()
        sessionStorage.remove()
    }
}