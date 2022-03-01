package com.example.tetrainingandroid.ui.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.architecture.BaseFragment
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.User
import com.example.tetrainingandroid.databinding.AccountFragmentBinding
import com.example.tetrainingandroid.extensions.ImageType
import com.example.tetrainingandroid.extensions.load
import com.example.tetrainingandroid.ui.main.UserViewModel
import com.example.tetrainingandroid.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment: BaseFragment(R.layout.account_fragment) {
    private val viewModel: UserViewModel by activityViewModels()
    private var binding: AccountFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AccountFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val userObserver = Observer<User> {
        binding.txtName.text = it?.name ?: it?.username ?: getString(R.string.unknown)
        binding.imgAvatar.load(
            it?.avatar?.getAvatarPath(),
            size = ImageConfiguration.Size.PROFILE,
            type = ImageType.AVATAR
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner, userObserver)
        initEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.user.removeObserver(userObserver)
    }

    private fun initEvent() {
        binding.txtFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_favoriteFragment)
        }
        binding.txtWatchlist.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_watchlistFragment)
        }
    }
}