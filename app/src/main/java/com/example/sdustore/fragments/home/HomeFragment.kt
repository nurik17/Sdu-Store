package com.example.sdustore.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sdustore.adapters.HomeViewPagerAdapter
import com.example.sdustore.databinding.FragmentHomeBinding
import com.example.sdustore.fragments.categories.*
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragment = arrayListOf<Fragment>(
            MainCategoryFragment(),
            SweetshotFragment(),
            TshirtsFragment(),
            ShoppersFragment(),
            CupsFragment()
        )

        val viewPagerAdapter = HomeViewPagerAdapter(categoriesFragment,
            requireActivity().supportFragmentManager, lifecycle)

        binding.viewPagerHome.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPagerHome){tab,position ->
            when(position){
                0 -> tab.text = "Свитшоты"
                1 -> tab.text = "Футболкы"
                2 -> tab.text = "Шопперы"
                3 -> tab.text = "Кружкы"
                4 -> tab.text = "Другие"
            }
        }.attach()
    }
}
