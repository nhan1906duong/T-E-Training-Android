package com.example.tetrainingandroid.ui.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.BaseFragment
import com.example.tetrainingandroid.databinding.SettingFragmentBinding
import com.example.tetrainingandroid.locale.LocaleUtils
import com.example.tetrainingandroid.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment: BaseFragment(R.layout.setting_fragment) {
    @Inject lateinit var utils: LocaleUtils
    private var binding: SettingFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener {
            logout()
        }

        binding.txtChangeLanguage.setOnClickListener {
            utils.switchLocale(context)
            restart()
        }
    }
}