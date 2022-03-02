package com.example.tetrainingandroid.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : CacheViewFragment<SplashViewModel>(R.layout.splash_fragment) {
    override val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent { SplashView() }
        }
    }

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewModel.loginData.observe(viewLifecycleOwner) { state ->
            when(state) {
                LoginState.ApiAuthorization -> {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
                LoginState.Login -> {
                    findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                }
                else -> {}
            }
        }
    }
}