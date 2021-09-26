package com.example.tetrainingandroid.ui.main.setting

import android.os.Bundle
import android.view.View
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.setting_fragment.*

@AndroidEntryPoint
class SettingFragment: BaseFragment(R.layout.setting_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogout.setOnClickListener {
            logout()
        }
    }
}