package com.example.sdustore.ui.loginRegister

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.sdustore.R
import com.example.sdustore.base.BaseFragment
import com.example.sdustore.data.Resource
import com.example.sdustore.data.extensions.setSafeOnClickListener
import com.example.sdustore.data.extensions.setUpBottomSheetDialog
import com.example.sdustore.databinding.LoginFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.integrity.internal.l
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val LOGIN_TAG = "LOGIN_TAG"

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>(LoginFragmentBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()


    override fun onBindView() {
        super.onBindView()
        setUpClickListeners()
        handleLoginResource()
        setUpClickListenerLoginBtn()
        setOnClickForgetPassword()
        handleForgotPassword()
    }

    private fun setUpClickListeners() {
        binding.textWelcome.setSafeOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    private fun setUpClickListenerLoginBtn(){
        binding.apply {
            btnLogIn.setSafeOnClickListener {
                val email = editEmail.text.toString().trim()
                val password = editPassword.text.toString()
                viewModel.login(email, password)
            }
        }
    }
    private fun handleLoginResource(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.login.collect {
                    when (it) {
                        is Resource.Loading ->{
                            binding.progressBar.visibility = View.VISIBLE
                            Log.d(LOGIN_TAG, "loading")
                        }
                        is Resource.Success ->{
                            binding.progressBar.visibility = View.INVISIBLE
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            Log.d(LOGIN_TAG, "success")
                        }
                        is Resource.Error ->{
                            binding.progressBar.visibility = View.GONE
                            Log.d(LOGIN_TAG, it.message.toString())
                        }
                        else-> Unit
                    }
                }
            }
        }
    }
    private fun setOnClickForgetPassword(){
        binding.forgotPassword.setSafeOnClickListener {
            setUpBottomSheetDialog { email->
                viewModel.resetPassword(email)
            }
        }
    }
    private fun handleForgotPassword(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.resetPassword.collect{
                    when (it) {
                        is Resource.Loading ->{
                            binding.progressBar.visibility = View.VISIBLE
                            Log.d(LOGIN_TAG, "loading")
                        }
                        is Resource.Success ->{
                            binding.progressBar.visibility = View.INVISIBLE
                            Snackbar.make(requireView(),"Reset link sent to your email",Snackbar.LENGTH_LONG).show()
                            Log.d(LOGIN_TAG, "success")
                        }
                        is Resource.Error ->{
                            binding.progressBar.visibility = View.GONE
                            Snackbar.make(requireView(),"Error ${it.message}",Snackbar.LENGTH_LONG).show()
                            Log.d(LOGIN_TAG, it.message.toString())
                        }
                        else-> Unit
                    }
                }
            }
        }
    }
}