package com.example.sdustore.feature.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sdustore.R
import com.example.sdustore.databinding.FragmentHomeBinding
import com.example.sdustore.feature.categories.*
import com.example.sdustore.feature.home.adapter.HomePageItemsAdapter
import com.example.sdustore.feature.home.adapter.HomeViewPagerAdapter
import com.example.sdustore.feature.home.data.ItemModel
import com.example.sdustore.utils.extensions.isNightMode
import com.example.sdustore.utils.extensions.setStatusBarContentColor
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemlist : ArrayList<ItemModel>
    private lateinit var adapter : HomePageItemsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireActivity().getSharedPreferences("MY_PRE",Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email","").toString()
        val password = sharedPreferences.getString("password","").toString()


        val isNightMode = requireContext().isNightMode()
        requireActivity().window?.setStatusBarContentColor(!isNightMode)

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

        itemlist = ArrayList()
        itemlist.add(ItemModel(R.drawable.main_item,"Свитер",17990))
        itemlist.add(ItemModel(R.drawable.main_item,"Свитер",17990))
        itemlist.add(ItemModel(R.drawable.main_item,"Свитер",17990))
        itemlist.add(ItemModel(R.drawable.main_item,"Свитер",17990))
        itemlist.add(ItemModel(R.drawable.main_item,"Свитер",17990))
        itemlist.add(ItemModel(R.drawable.main_item,"Свитер",17990))
        itemlist.add(ItemModel(R.drawable.main_item,"Свитер",17990))
        itemlist.add(ItemModel(R.drawable.main_item,"Свитер",17990))

        adapter = HomePageItemsAdapter(itemlist)

        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = GridLayoutManager(requireContext(),2)
    }
}
