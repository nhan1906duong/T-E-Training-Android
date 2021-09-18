package com.example.tetrainingandroid.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.home_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MSG", "onViewCreated call")
        parentLayout?.setOnClickListener {
            parentFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_detailFragment)
        }
    }
}