package com.example.sdustore.feature

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sdustore.R
import com.example.sdustore.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater,container,false)

        Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)

        },3000)


        return binding.root
    }

   /* private fun onBoardIsFinished() :Boolean{
        val sharedPreference = requireActivity().getSharedPreferences("onBoarding",Context.MODE_PRIVATE)
        return sharedPreference.getBoolean("finished",true)*/
    }