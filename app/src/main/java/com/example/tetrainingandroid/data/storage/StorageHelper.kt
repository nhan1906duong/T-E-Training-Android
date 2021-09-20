package com.example.tetrainingandroid.data.storage

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageHelper @Inject constructor(
    private val loginStorage: LoginStorage,
    private val requestStorage: RequestTokenResponseStorage,
    private val sessionStorage: SessionStorage,
){
    fun removeUserCache() {
        loginStorage.remove()
        requestStorage.remove()
        sessionStorage.remove()
    }
}