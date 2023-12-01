package com.example.sdustore.ui.category.allProduct

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sdustore.base.BaseFragment
import com.example.sdustore.data.entity.Resource
import com.example.sdustore.ui.category.adapter.AllProductAdapter
import com.example.sdustore.data.extensions.OffsetDecoration
import com.example.sdustore.databinding.FragmentAllProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val ALL_PROD_FRAG = "ALL_PR_FRAG"
@AndroidEntryPoint
class AllProductFragment :
    BaseFragment<FragmentAllProductBinding>(FragmentAllProductBinding::inflate) {

    private val allProductAdapter by lazy {
        AllProductAdapter { item ->
            onProductClick()
        }
    }
    private val viewModel: AllProductViewModel by viewModels()

    override fun onBindView() {
        super.onBindView()
        setUpRecyclerView()
        handleStateAllProducts()
    }


    private fun setUpRecyclerView() {
        binding.apply {
            allProductRv.layoutManager =
                GridLayoutManager(requireContext(),2,  GridLayoutManager.VERTICAL, false)
            allProductRv.adapter = allProductAdapter
            allProductRv.addItemDecoration(OffsetDecoration(start = 8, top = 5, end = 0, bottom = 5))
        }
    }

    private fun handleStateAllProducts(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.allProducts.collectLatest{
                    when(it){
                        is Resource.Loading ->{
                            binding.progressBarAllProduct.visibility = View.VISIBLE
                        }
                        is Resource.Success->{
                            binding.progressBarAllProduct.visibility = View.GONE
                            it.data?.let { it1 -> allProductAdapter.setData(it1) }
                            Log.d(ALL_PROD_FRAG, it.data.toString())
                        }
                        is Resource.Error->{
                            binding.progressBarAllProduct.visibility = View.GONE
                            Log.d(ALL_PROD_FRAG, it.message.toString())
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
