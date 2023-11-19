package com.example.sdustore.ui

import androidx.navigation.fragment.findNavController
import com.example.sdustore.R
import com.example.sdustore.base.BaseFragment
import com.example.sdustore.data.extensions.setSafeOnClickListener
import com.example.sdustore.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>(LoginFragmentBinding::inflate) {

    override fun onBindView() {
        super.onBindView()
        binding.textWelcome.setSafeOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}