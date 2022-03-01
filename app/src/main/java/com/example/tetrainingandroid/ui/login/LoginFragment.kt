package com.example.tetrainingandroid.ui.login

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.BaseFragment
import com.example.tetrainingandroid.data.storage.LoginStorage
import com.example.tetrainingandroid.databinding.LoginFragmentBinding
import com.example.tetrainingandroid.utils.autoCleared
import com.example.tetrainingandroid.validate.Validation
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: BaseFragment(R.layout.login_fragment) {
    @Inject lateinit var loginStorage: LoginStorage
    private var binding: LoginFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        onSignInResult(result)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            loginSuccess()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEvents()
    }

    private fun setEvents() {
        binding.apply {
            btnLogin.setOnClickListener {
                hideKeyboard()
                val username = edtUsername.text?.toString()
                val password = edtPassword.text?.toString()
                if (validate(username, password)) {
                    login(username!!, password!!)
                }
            }

            btnFacebook.setOnClickListener {
                loginViaSocialMedia(AuthUI.IdpConfig.FacebookBuilder().build())
            }

            btnGoogle.setOnClickListener {
                loginViaSocialMedia(AuthUI.IdpConfig.GoogleBuilder().build())
            }

            parentLayout.setOnClickListener { hideKeyboard() }
        }
    }

    private fun validate(username: String?, password: String?): Boolean {
        var isValidate = true
        if (username.isNullOrEmpty()) {
            binding.edtUsername.error = getString(R.string.validate_username)
            isValidate = false
        }
        if (password.isNullOrEmpty()) {
            binding.edtPassword.error = getString(R.string.validate_password)
            isValidate = false
        }
        return isValidate
    }

    private fun login(username: String, password: String) {
        if (Validation.isValidAccount(username, password)) {
            loginSuccess()
        } else {
            LoginFailedDialog().show(childFragmentManager, "TAG")
        }
    }

    private fun loginViaSocialMedia(idpConfig: AuthUI.IdpConfig) {
        val providers = arrayListOf(idpConfig)

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun loginSuccess() {
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        loginStorage.save(true)
    }
}