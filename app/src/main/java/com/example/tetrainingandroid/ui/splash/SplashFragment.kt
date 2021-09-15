package com.example.tetrainingandroid.ui.splash

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.deeplink.AuthorizePermissionHelper
import com.example.tetrainingandroid.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.splash_fragment) {
    private val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var permissionHelper: AuthorizePermissionHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
        observer()
    }

    private fun fetchData() {
        viewModel.getTokenRequest()
    }

    private fun observer() {
        viewModel.data.observe(viewLifecycleOwner, { hasToken ->
            when(hasToken) {
                true -> {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
                false -> {
                    Toast.makeText(context, "Request token failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}