package com.example.tetrainingandroid.ui.splash

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.splash_fragment) {
    private val viewModel: SplashViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as Activity).window.statusBarColor = Color.WHITE
    }

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
                    Toast.makeText(context, viewModel.exceptionHandler.errorMessage.value, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}