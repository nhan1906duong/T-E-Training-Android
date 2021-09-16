package com.example.tetrainingandroid.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.storage.SessionStorage
import com.example.tetrainingandroid.validate.Validation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.login_fragment) {
    @Inject lateinit var sessionStorage: SessionStorage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            loginViaFacebook()
        }

        btnGoogle.setOnClickListener {
            loginViaGoogle()
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

    private fun loginViaFacebook() {

    }

    private fun loginViaGoogle() {

    }

    private fun hideKeyboard() {
        activity?.run {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
            currentFocus?.clearFocus()
        }
    }
}