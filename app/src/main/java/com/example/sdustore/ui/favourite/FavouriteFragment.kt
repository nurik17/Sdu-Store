package com.example.sdustore.ui.favourite

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sdustore.base.BaseFragment
import com.example.sdustore.data.entity.Product
import com.example.sdustore.data.extensions.OffsetDecoration
import com.example.sdustore.databinding.FragmentFavouriteBinding
import com.example.sdustore.ui.category.ProductsFragmentDirections
import com.example.sdustore.ui.category.adapter.AllProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment: BaseFragment<FragmentFavouriteBinding>(FragmentFavouriteBinding::inflate) {

    private val favouriteAdapter by lazy {
        AllProductAdapter { item ->
            onProductClick(item)
        }
    }

    override fun onBindView() {
        super.onBindView()
        setUpRecyclerView()

    }
    private fun setUpRecyclerView() {
        binding.apply {
            rvFavourite.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            rvFavourite.adapter = favouriteAdapter
            rvFavourite.addItemDecoration(
                OffsetDecoration(
                    start = 8,
                    top = 5,
                    end = 0,
                    bottom = 5
                )
            )
        }
    }

    private fun onProductClick(item: Product) {
        val action = ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(item)
        findNavController().navigate(action)
    }
}