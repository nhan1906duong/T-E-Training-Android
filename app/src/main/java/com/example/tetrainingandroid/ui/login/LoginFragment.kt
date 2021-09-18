package com.example.tetrainingandroid.ui.login

import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.data.storage.SessionStorage
import com.example.tetrainingandroid.validate.Validation
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: CacheViewFragment(R.layout.login_fragment) {
    @Inject lateinit var sessionStorage: SessionStorage

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        onSignInResult(result)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        //val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            //val user = FirebaseAuth.getInstance().currentUser
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        setEvents()
    }

    private fun setEvents() {
        btnLogin.setOnClickListener {
            hideKeyboard()
            val username = edtUsername?.text?.toString()
            val password = edtPassword?.text?.toString()
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

        parentLayout?.setOnClickListener { hideKeyboard() }
    }

    private fun validate(username: String?, password: String?): Boolean {
        var isValidate = true
        if (username.isNullOrEmpty()) {
            edtUsername?.error = getString(R.string.validate_username)
            isValidate = false
        }
        if (password.isNullOrEmpty()) {
            edtPassword?.error = getString(R.string.validate_password)
            isValidate = false
        }
        return isValidate
    }

    private fun login(username: String, password: String) {
        if (Validation.isValidAccount(username, password)) {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            sessionStorage.resetToken()
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

    private fun hideKeyboard() {
        activity?.run {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
            currentFocus?.clearFocus()
        }
    }
}