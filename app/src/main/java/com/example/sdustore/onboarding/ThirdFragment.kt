package com.example.sdustore.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.sdustore.R
import com.example.sdustore.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(inflater,container,false)

        val viewpager = activity?.findViewById<ViewPager2>(R.id.viewpager)

        binding.nextText.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_introductionFragment)

        }
        return binding.root
    }


 /*   private fun onBoardIsFinished(){
        val sharedPreference = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean("finished",true)
        editor.apply()
    }*/
}