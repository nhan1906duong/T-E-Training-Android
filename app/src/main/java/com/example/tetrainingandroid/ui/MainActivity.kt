package com.example.tetrainingandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.*
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.ui.splash.SplashFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) setUpFragment() // ensure add only once
    }

    private fun setUpFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<SplashFragment>(R.id.fragment_container_view)
        }
    }

}