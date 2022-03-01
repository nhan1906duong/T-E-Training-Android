package com.example.tetrainingandroid.architecture

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.data.storage.LanguageStorage
import com.example.tetrainingandroid.data.storage.StorageHelper
import com.example.tetrainingandroid.ui.MainActivity
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes contentLayoutId: Int): Fragment(contentLayoutId) {
    @Inject lateinit var storageHelper: StorageHelper
    @Inject lateinit var languageStorage: LanguageStorage

    private var isViewCreatedBefore: Boolean = false

    open fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isViewCreatedBefore) {
            isViewCreatedBefore = true
            onViewCreatedFirstTime(view, savedInstanceState)
            view.findViewById<Toolbar>(R.id.toolbar)?.apply {
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener {
                    activity?.onBackPressed()
                }
            }
        }
    }

    protected fun hideKeyboard() {
        activity?.run {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
            currentFocus?.clearFocus()
        }
    }

    protected fun logout() {
        storageHelper.removeUserCache()
        findNavController().setGraph(R.navigation.nav_graph)
    }

    protected fun restart() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finishAffinity()
    }
}