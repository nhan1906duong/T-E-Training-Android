package com.example.tetrainingandroid.architecture

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.example.tetrainingandroid.extensions.toast

abstract class LoadingDataFragment<VM : BaseViewModel>(@LayoutRes contentLayoutId: Int) :
    BaseFragment(contentLayoutId) {

    protected abstract val viewModel: VM

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        viewModel.loadData()
        observeError()
    }

    private fun observeError() {
        viewModel.getError().observe(viewLifecycleOwner, {
            onLoadingDataError(it)
        })
    }

    open fun onLoadingDataError(message: String?) {
        toast(message)
    }
}