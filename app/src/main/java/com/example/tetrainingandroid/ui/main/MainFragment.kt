package com.example.tetrainingandroid.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.BaseFragment
import com.example.tetrainingandroid.databinding.MainFragmentBinding
import com.example.tetrainingandroid.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: BaseFragment(R.layout.main_fragment) {
    @Inject lateinit var viewPagerAdapter: MainFragmentStateAdapter

    private val userViewModel: UserViewModel by activityViewModels()
    private var binding: MainFragmentBinding by autoCleared()

    private var currentIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

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
        currentIndex = binding.pager.currentItem
        removeViewPagerAdapter() // before Fragment save view instance state
    }

    private fun setUpViewPager() {
        binding.pager.apply {
            offscreenPageLimit = 4
            setAdapter()
            if (adapter == null) {
                adapter = viewPagerAdapter
                if (currentIndex != 0) {
                    doOnPreDraw {
                        currentItem = currentIndex
                    } // or use post (ViewTreeObserver)
                }
            }
        }
        disableViewPagerSwipe()
    }

    private fun disableViewPagerSwipe() = false.also { binding.pager.isUserInputEnabled = it }

    private fun setupBottomNavigationBar() {
        binding.apply {
            bottomNavigationBar.setOnItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.home -> pager.currentItem = 0
                    R.id.search -> pager.currentItem = 1
                    R.id.account -> pager.currentItem = 2
                    R.id.setting -> pager.currentItem = 3
                }
                true
            }
        }
    }

    private fun removeViewPagerAdapter() {
        binding.pager.adapter = null
    }

    private fun setAdapter() {
        binding.pager.apply {
            if (adapter == null) {
                adapter = viewPagerAdapter
                if (currentIndex != 0) {
                    doOnPreDraw {
                        currentItem = currentIndex
                    } // or use post (ViewTreeObserver)
                }
            }
        }
    }
}