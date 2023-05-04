package com.example.sdustore.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sdustore.adapters.ViewPagerAdapter
import com.example.sdustore.databinding.FragmentOnBoardingBinding
import com.example.sdustore.utils.extensions.isNightMode
import com.example.sdustore.utils.extensions.setStatusBarContentColor


class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        val isNightMode = requireContext().isNightMode()
        requireActivity().window?.setStatusBarContentColor(isNightMode)

        val fragmentList = arrayListOf<Fragment>(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList, requireActivity().supportFragmentManager,
            lifecycle
        )

        val viewPager = binding.viewpager
        viewPager.adapter = adapter

        val indicator = binding.dotsIndicator
        indicator.attachTo(viewPager)

        return binding.root
    }
}