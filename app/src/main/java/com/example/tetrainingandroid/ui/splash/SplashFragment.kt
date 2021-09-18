package com.example.tetrainingandroid.ui.splash

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : CacheViewFragment(R.layout.splash_fragment) {
    private val viewModel: SplashViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as Activity).window.statusBarColor = Color.WHITE
    }

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewModel.loginData.observe(viewLifecycleOwner) { hasToken ->
            when(hasToken) {
                true -> {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
                false -> {
                    Toast.makeText(context, viewModel.exceptionHandler.errorMessage.value, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}