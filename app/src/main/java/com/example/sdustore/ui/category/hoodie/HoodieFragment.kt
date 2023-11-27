package com.example.sdustore.ui.category.hoodie

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sdustore.base.BaseFragment
import com.example.sdustore.data.Resource
import com.example.sdustore.databinding.FragmentAllProductBinding
import com.example.sdustore.ui.category.adapter.AllProductAdapter
import com.example.sdustore.utils.OffsetDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HoodieFragment :
    BaseFragment<FragmentAllProductBinding>(FragmentAllProductBinding::inflate) {

    private val allProductAdapter by lazy {
        AllProductAdapter { item ->
            onProductClick()
        }
    }
    private val viewModel: HoodieViewModel by viewModels()

    override fun onBindView() {
        super.onBindView()
        setUpRecyclerView()
        handleStateAllProducts()
    }


    private fun setUpRecyclerView() {
        binding.apply {
            allProductRv.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            allProductRv.adapter = allProductAdapter
            allProductRv.addItemDecoration(
                OffsetDecoration(
                    start = 8,
                    top = 5,
                    end = 0,
                    bottom = 5
                )
            )
        }
    }

    private fun handleStateAllProducts(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.hoodieProduct.collectLatest{
                    when(it){
                        is Resource.Loading ->{
                            binding.progressBarAllProduct.visibility = View.VISIBLE
                        }
                        is Resource.Success ->{
                            binding.progressBarAllProduct.visibility = View.GONE
                            it.data?.let { it1 -> allProductAdapter.setData(it1) }
                        }
                        is Resource.Error ->{
                            binding.progressBarAllProduct.visibility = View.GONE
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
    private fun onProductClick() {
    }
}