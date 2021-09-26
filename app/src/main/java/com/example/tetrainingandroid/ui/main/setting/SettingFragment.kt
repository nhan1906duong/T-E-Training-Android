package com.example.tetrainingandroid.ui.main.setting

import android.os.Bundle
import android.view.View
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.BaseFragment
import com.example.tetrainingandroid.locale.LocaleUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.setting_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment: BaseFragment(R.layout.setting_fragment) {
    @Inject lateinit var utils: LocaleUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogout.setOnClickListener {
            logout()
        }

        txtChangeLanguage?.setOnClickListener {
            utils.switchLocale(context)
            restart()
        }
    }
}