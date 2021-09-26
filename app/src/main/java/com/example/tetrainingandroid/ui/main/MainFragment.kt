package com.example.tetrainingandroid.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: BaseFragment(R.layout.main_fragment) {
    @Inject lateinit var viewPagerAdapter: MainFragmentStateAdapter

    private val userViewModel: UserViewModel by activityViewModels()

    private var currentIndex = 0

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        userViewModel.getUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
        setupBottomNavigationBar()
    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }

    override fun onStop() {
        super.onStop()
        currentIndex = pager?.currentItem ?: 0
        removeViewPagerAdapter() // before Fragment save view instance state
    }

    private fun setUpViewPager() {
        pager?.offscreenPageLimit = 4
        setAdapter()
        if (pager?.adapter == null) {
            pager?.adapter = viewPagerAdapter
            if (currentIndex != 0) {
                pager?.doOnPreDraw {
                    pager?.currentItem = currentIndex
                } // or use post (ViewTreeObserver)
            }
        }
        disableViewPagerSwipe()
    }

    private fun disableViewPagerSwipe() = false.also { pager?.isUserInputEnabled = it }

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

    private fun setAdapter() {
        if (pager?.adapter == null) {
            pager?.adapter = viewPagerAdapter
            if (currentIndex != 0) {
                pager?.doOnPreDraw {
                    pager?.currentItem = currentIndex
                } // or use post (ViewTreeObserver)
            }
        }
    }
}