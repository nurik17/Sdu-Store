package com.example.sdustore.feature

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sdustore.R
import com.example.sdustore.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        val spannable = SpannableStringBuilder(getString(R.string.don_t_have_account_register))
        spannable.setSpan(
            ForegroundColorSpan(Color.RED),
            20,
            28,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.RegisterBtn.setOnClickListener{
            findNavController()
                .navigate(R.id.action_loginFragment_to_homeFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}