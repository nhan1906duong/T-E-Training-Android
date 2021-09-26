package com.example.tetrainingandroid.architecture

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.extensions.toast
import com.example.tetrainingandroid.ui.splash.SplashFragment
import com.facebook.login.LoginFragment

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
        viewModel.authorizeState().observe(viewLifecycleOwner, { isAuthorize ->
            if (isAuthorize == false && this !is SplashFragment && this !is LoginFragment) {
                logout()
            }
        })
    }

    open fun onLoadingDataError(message: String?) {
        toast(message)
    }
}