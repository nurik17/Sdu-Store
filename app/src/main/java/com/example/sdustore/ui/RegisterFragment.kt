package com.example.sdustore.ui

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.sdustore.R
import com.example.sdustore.base.BaseFragment
import com.example.sdustore.data.Resource
import com.example.sdustore.data.User
import com.example.sdustore.data.extensions.RegisterValidation
import com.example.sdustore.data.extensions.setSafeOnClickListener
import com.example.sdustore.databinding.RegisterFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val REGISTER_TAG = "REGISTER_TAG"

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterFragmentBinding>(RegisterFragmentBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()
    override fun onBindView() {
        super.onBindView()
        setUpClickListeners()
        handleRegisterResource()
        handleValidationSource()
    }

    private fun handleRegisterResource(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.register.collect {
                    when (it) {
                        is Resource.Loading -> {
                            binding.btnSignUp.isEnabled = true
                        }

                        is Resource.Success -> {
                            binding.btnSignUp.isEnabled = true
                            Log.d(REGISTER_TAG, it.data.toString())
                        }

                        is Resource.Error -> {
                            binding.btnSignUp.isEnabled = false
                            Log.e(REGISTER_TAG, it.message.toString())
                        }
                    }
                }
            }
        }
    }

    private fun handleValidationSource(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.validation.collect { validation ->
                    if (validation.email is RegisterValidation.Failed) {
                        withContext(Dispatchers.Main) {
                            binding.editEmail.apply {
                                requestFocus()
                                error = validation.email.message
                            }
                        }
                    }
                    if (validation.password is RegisterValidation.Failed) {
                        withContext(Dispatchers.Main) {
                            binding.editPassword.apply {
                                requestFocus()
                                error = validation.password.message
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUpClickListeners(){
        binding.textSignUp.setSafeOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.apply {
            btnSignUp.setSafeOnClickListener {
                val user = User(
                    editFirstName.text.toString().trim(),
                    editLastName.text.toString().trim(),
                    editEmail.text.toString().trim(),
                )
                val password = editPassword.text.toString()
                viewModel.createAccountWithEmailPassword(user, password)
            }
        }
    }
}