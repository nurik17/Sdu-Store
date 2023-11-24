package com.example.sdustore.ui.home

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sdustore.base.BaseFragment
import com.example.sdustore.data.MainRecyclerData
import com.example.sdustore.data.Resource
import com.example.sdustore.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val HOME_FRAG_TAG = "HOME_FRAG_TAG"

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val newsAdapter by lazy {
        MainRecyclerAdapter()
    }
    override fun onBindView() {
        super.onBindView()
        setUpNewsRecyclerView()
        handleStateNewsItems()
    }

    private fun setUpNewsRecyclerView(){
        binding.newsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.newsRv.adapter = newsAdapter
    }
    private fun handleStateNewsItems(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.newsAdapterItems.collectLatest {
                    when(it){
                        is Resource.Loading->{
                            binding.progressBarNews.visibility = View.VISIBLE
                            inVisibleComponents()
                        }
                        is Resource.Success->{
                            binding.progressBarNews.visibility = View.GONE
                            visibleComponents()
                            it.data?.let { it1 -> newsAdapter.setData(it1) }
                        }
                        is Resource.Error->{
                            binding.progressBarNews.visibility = View.GONE
                            inVisibleComponents()
                            Snackbar.make(requireView(),"Check your internet connection",
                                Snackbar.LENGTH_LONG).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun visibleComponents(){
        binding.blueLayout.visibility = View.VISIBLE
        binding.cardTShirt.visibility = View.VISIBLE
        binding.cardHudi.visibility = View.VISIBLE
        binding.cardSweetShot.visibility = View.VISIBLE
        binding.textTopCategory.visibility = View.VISIBLE
        binding.allLayout.visibility = View.VISIBLE
    }
    private fun inVisibleComponents(){
        binding.blueLayout.visibility = View.INVISIBLE
        binding.cardTShirt.visibility = View.INVISIBLE
        binding.cardHudi.visibility = View.INVISIBLE
        binding.cardSweetShot.visibility = View.INVISIBLE
        binding.textTopCategory.visibility = View.INVISIBLE
        binding.allLayout.visibility = View.INVISIBLE
    }
}