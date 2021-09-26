package com.example.tetrainingandroid.ui.main.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.CacheViewFragment
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.ui.main.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.account_fragment.*

@AndroidEntryPoint
class AccountFragment: CacheViewFragment<UserViewModel>(R.layout.account_fragment) {
    override val viewModel: UserViewModel by activityViewModels()

    override fun onViewCreatedFirstTime(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedFirstTime(view, savedInstanceState)
        observerData()
    }

    private fun observerData() {
        viewModel.user.observe(viewLifecycleOwner, {
            txtName.text = it?.name ?: it?.username ?: getString(R.string.unknown)
            imgAvatar.load(
                it?.avatar?.getAvatarPath(),
                size = ImageConfiguration.Size.PROFILE,
                type = ImageType.AVATAR
            )
        })
    }
}