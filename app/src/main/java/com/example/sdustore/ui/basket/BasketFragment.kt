package com.example.sdustore.ui.basket

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sdustore.R
import com.example.sdustore.base.BaseFragment
import com.example.sdustore.data.entity.Resource
import com.example.sdustore.data.extensions.OffsetDecoration
import com.example.sdustore.databinding.FragmentBasketBinding
import com.example.sdustore.ui.category.adapter.BasketAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketFragment : BaseFragment<FragmentBasketBinding>(FragmentBasketBinding::inflate) {

    private val viewModel: BasketViewModel by viewModels()

    private val basketAdapter by lazy {
        BasketAdapter()
    }

    override fun onBindView() {
        super.onBindView()

        setUpRv()
        handleOfData()
    }

    private fun setUpRv(){
        binding.apply {
            rvBasket.adapter = basketAdapter
            rvBasket.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            rvBasket.addItemDecoration(OffsetDecoration(
                bottom = 10
            ))
        }
    }
    private fun handleOfData(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.cartProduct.collectLatest {
                    when(it){
                        is Resource.Loading -> {
                            binding.progressBarBasket.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            binding.progressBarBasket.visibility = View.INVISIBLE
                            if(it.data!!.isEmpty()){
                                binding.emptyBox.visibility = View.VISIBLE
                            }else{
                                binding.emptyBox.visibility = View.INVISIBLE
                                basketAdapter.setData(it.data)
                            }
                        }

                        is Resource.Error -> {
                            binding.progressBarBasket.visibility = View.GONE
                            Snackbar.make(requireView(),it.message.toString(),
                                Snackbar.LENGTH_LONG).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}