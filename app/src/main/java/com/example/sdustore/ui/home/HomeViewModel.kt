package com.example.sdustore.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdustore.data.entity.MainRecyclerData
import com.example.sdustore.data.entity.Resource
import com.example.sdustore.data.useCases.MainRecyclerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRecyclerUseCase: MainRecyclerUseCase
) : ViewModel() {

    private val _newsAdapterItems =
        MutableStateFlow<Resource<List<MainRecyclerData>>>(Resource.UnSpecified())
    val newsAdapterItems = _newsAdapterItems.asStateFlow()

    init {
        fetchSpecialProducts()
    }

    private fun fetchSpecialProducts() {
        viewModelScope.launch {
            try {
                _newsAdapterItems.emit(Resource.Loading())
                when (val result = mainRecyclerUseCase.execute()) {
                    is Resource.Success -> {
                        _newsAdapterItems.emit(result)
                        Log.d("HomeViewModel", "fetchSpecialProducts:${result.data}")
                    }
                    is Resource.Error -> {
                        _newsAdapterItems.emit(result)
                        Log.e("HomeViewModel", "Error fetching all products: ${result.message}")
                    }
                    else-> Unit
                }
            } catch (e: Exception) {
                _newsAdapterItems.emit(Resource.Error(e.message.toString()))
            }
        }
    }
}