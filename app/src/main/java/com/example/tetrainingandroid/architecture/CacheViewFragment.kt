package com.example.tetrainingandroid.architecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.example.tetrainingandroid.extensions.removeFromParent

open class CacheViewFragment(@LayoutRes contentLayoutId: Int): BaseFragment(contentLayoutId)  {
    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(rootView == null) {
            rootView = super.onCreateView(inflater, container, savedInstanceState)
        } else {
            rootView?.removeFromParent()
        }
        return rootView
    }
}