package com.example.tetrainingandroid.ui.main

import android.os.Bundle
import android.view.View
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: BaseFragment(R.layout.main_fragment) {
    @Inject lateinit var viewPagerAdapter: MainFragmentStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
        setupBottomNavigationBar()
    }

    override fun onStart() {
        super.onStart()
        pager?.adapter = viewPagerAdapter
    }

    override fun onStop() {
        super.onStop()
        removeViewPagerAdapter() // before Fragment save view instance state
    }

    private fun setUpViewPager() {
        pager?.offscreenPageLimit = 4
        pager?.adapter = viewPagerAdapter
        pager?.isUserInputEnabled = false
    }

    private fun setupBottomNavigationBar() {
        bottomNavigationBar?.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> pager?.currentItem = 0
                R.id.search -> pager?.currentItem = 1
                R.id.account -> pager?.currentItem = 2
                R.id.setting -> pager?.currentItem = 3
            }
            true
        }
    }

    private fun removeViewPagerAdapter() {
        pager?.adapter = null
    }
}