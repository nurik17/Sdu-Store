package com.example.sdustore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sdustore.R
import com.example.sdustore.databinding.FragmentIntroductionBinding

class IntroductionFragment : Fragment() {
    private lateinit var binding : FragmentIntroductionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroductionBinding.inflate(inflater, container, false)

        binding.startBtn.setOnClickListener{
            findNavController()
                .navigate(R.id.action_introductionFragment_to_accountOptionsFragment)
        }
        return binding.root
    }
}