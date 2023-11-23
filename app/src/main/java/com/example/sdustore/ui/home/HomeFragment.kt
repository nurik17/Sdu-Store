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
                        }
                        is Resource.Success->{
                            binding.progressBarNews.visibility = View.INVISIBLE
                            it.data?.let { it1 -> newsAdapter.setData(it1) }
                            Log.d(HOME_FRAG_TAG,"success")
                        }
                        is Resource.Error->{
                            binding.progressBarNews.visibility = View.GONE
                            Log.e(HOME_FRAG_TAG, it.message.toString())
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

}