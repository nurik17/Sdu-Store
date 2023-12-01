package com.example.sdustore.ui.category.allProduct

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdustore.data.entity.Product
import com.example.sdustore.data.entity.Resource
import com.example.sdustore.data.useCases.AllProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllProductViewModel @Inject constructor(
    private val allProductUseCase: AllProductUseCase
) : ViewModel(){

    private val _allProducts = MutableStateFlow<Resource<List<Product>>>(Resource.UnSpecified())
    val allProducts = _allProducts.asStateFlow()

    init {
        fetchAllProducts()
    }

    private fun fetchAllProducts(){
        viewModelScope.launch {
            try {
                _allProducts.emit(Resource.Loading())
                when (val result = allProductUseCase.execute()) {
                    is Resource.Success -> {
                        _allProducts.emit(result)
                        Log.d("AllProductViewModel", "fetchSpecialProducts:${result.data}")
                    }
                    is Resource.Error -> {
                        _allProducts.emit(result)
                        Log.e("AllProductViewModel", "Error fetching all products: ${result.message}")
                    }
                    else-> Unit
                }
            } catch (e: Exception) {
                _allProducts.emit(Resource.Error(e.message.toString()))
            }
        }
    }
}