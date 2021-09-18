package com.example.tetrainingandroid.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.tetrainingandroid.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.main_fragment) {
    @Inject lateinit var viewPagerAdapter: MainFragmentStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        removeViewPagerAdapter() // before Fragment save view instance state
    }

    private fun setUpViewPager(savedInstanceState: Bundle?) {
        savedInstanceState?.let { viewPagerAdapter }
        pager?.adapter = viewPagerAdapter
    }

    private fun removeViewPagerAdapter() {
        pager?.adapter = null
    }

}