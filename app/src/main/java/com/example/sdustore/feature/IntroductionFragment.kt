package com.example.sdustore.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sdustore.R
import com.example.sdustore.databinding.FragmentIntroductionBinding
import com.example.sdustore.utils.extensions.isNightMode
import com.example.sdustore.utils.extensions.setStatusBarContentColor

class IntroductionFragment : Fragment() {
    private lateinit var binding : FragmentIntroductionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroductionBinding.inflate(inflater, container, false)
        val isNightMode = requireContext().isNightMode()
        requireActivity().window?.setStatusBarContentColor(!isNightMode)
        binding.startBtn.setOnClickListener {
            findNavController().navigate(R.id.action_introductionFragment_to_accountOptionsFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}