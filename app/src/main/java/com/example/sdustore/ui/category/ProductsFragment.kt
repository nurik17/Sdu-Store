package com.example.sdustore.ui.category

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import com.example.sdustore.R
import com.example.sdustore.base.BaseFragment
import com.example.sdustore.databinding.FragmentProductsBinding
import com.example.sdustore.ui.category.adapter.ProductViewPagerAdapter
import com.example.sdustore.ui.category.allProduct.AllProductFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class ProductsFragment : BaseFragment<FragmentProductsBinding>(FragmentProductsBinding::inflate) {

    override fun onBindView() {
        super.onBindView()

        backgroundTabLayout()

        val categoriesFragment = arrayListOf<Fragment>(
            AllProductFragment(),
            HoodieFragment(),
            TShirtFragment(),
            SweetShotFragment(),
            SweetShotFragment(),
            SweetShotFragment(),
        )
        val viewPagerAdapter =
            ProductViewPagerAdapter(categoriesFragment, childFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position->
            when(position){
                0->tab.text = "Все"
                1->tab.text = "Hoodie"
                2->tab.text = "T-Shirt"
                3->tab.text = "SweetShot"
                4->tab.text = "SweetShot"
                5->tab.text = "SweetShot"
            }
        }.attach()
    }

    @SuppressLint("ResourceAsColor")
    private fun backgroundTabLayout(){
        val tabLayout = binding.tabLayout

        val selectedTabBackground = getDrawable(requireContext(),R.drawable.selected_tab_background)
        val defaultTabBackground = getDrawable(requireContext(),R.drawable.selected_tab_background)

        val firstTab = tabLayout.getTabAt(0)
        firstTab?.view?.background = defaultTabBackground
        firstTab?.view?.setBackgroundColor(R.color.white)

        for(i in 1 until tabLayout.tabCount){
            val tab = tabLayout.getTabAt(i)
            tab?.view?.background = selectedTabBackground
            tab?.view?.setBackgroundColor(R.color.white)
        }

        tabLayout.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.view?.background = selectedTabBackground
                firstTab?.view?.background = selectedTabBackground
                firstTab?.view?.setBackgroundColor(R.color.white)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.view?.background = null
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.view?.background = selectedTabBackground
            }
        })
    }
}