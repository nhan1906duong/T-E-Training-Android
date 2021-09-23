package com.example.tetrainingandroid.ui.cast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.cast_fragment.*

@AndroidEntryPoint
class CastFragment: CacheViewFragment<CastViewModel>(R.layout.cast_fragment) {
    override val viewModel: CastViewModel by viewModels()
    private val args: CastFragmentArgs by navArgs()

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        savedInstanceState?.putInt("castId", args.castId)
        observerData()
    }

    private fun observerData() {
        viewModel.people.observe(viewLifecycleOwner, { cast ->
            txtCastName?.text = cast?.name ?: ""
        })
    }

}