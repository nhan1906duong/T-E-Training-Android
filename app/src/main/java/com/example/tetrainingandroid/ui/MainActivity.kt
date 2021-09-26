package com.example.tetrainingandroid.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.locale.LocaleUtilsWrapper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun attachBaseContext(newBase: Context) {
        val locale = EntryPointAccessors.fromApplication(newBase, LocaleUtilsWrapper::class.java).utils
        super.attachBaseContext(locale.setupLocale(newBase))
    }
}