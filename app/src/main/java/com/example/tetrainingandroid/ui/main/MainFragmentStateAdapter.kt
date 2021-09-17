package com.example.tetrainingandroid.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tetrainingandroid.ui.main.account.AccountFragment
import com.example.tetrainingandroid.ui.main.home.HomeFragment
import com.example.tetrainingandroid.ui.main.search.SearchFragment
import com.example.tetrainingandroid.ui.main.setting.SettingFragment
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class MainFragmentStateAdapter @Inject constructor(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private val fragments = mutableListOf(
        HomeFragment(),
        SearchFragment(),
        AccountFragment(),
        SettingFragment()
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}