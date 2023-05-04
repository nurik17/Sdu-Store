package com.example.sdustore.feature.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sdustore.R
import com.example.sdustore.databinding.FragmentAccountOptionsBinding

class AccountOptionsFragment : Fragment() {
    private lateinit var binding: FragmentAccountOptionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountOptionsBinding.inflate(inflater,container,false)

        binding.RegisterBtn.setOnClickListener{
            findNavController()
                .navigate(R.id.action_accountOptionsFragment_to_registerFragment)
        }
        binding.loginBtn.setOnClickListener{
            findNavController()
                .navigate(R.id.action_accountOptionsFragment_to_loginFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}