package com.example.tetrainingandroid.architecture

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.tetrainingandroid.extensions.toast

abstract class BaseFragment(@LayoutRes contentLayoutId: Int): Fragment(contentLayoutId) {
    private var isViewCreatedBefore: Boolean = false

    open fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isViewCreatedBefore) {
            isViewCreatedBefore = true
            onViewCreatedFirstTime(view, savedInstanceState)
        }
    }
}