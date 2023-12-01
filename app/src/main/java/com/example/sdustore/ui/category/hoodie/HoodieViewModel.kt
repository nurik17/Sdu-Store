package com.example.sdustore.ui.category.hoodie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdustore.data.entity.Product
import com.example.sdustore.data.entity.Resource
import com.example.sdustore.data.useCases.HoodieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoodieViewModel @Inject constructor(
    private val hoodieUseCase: HoodieUseCase
) : ViewModel(){

    private val _hoodieProduct = MutableStateFlow<Resource<List<Product>>>(Resource.UnSpecified())
    val hoodieProduct = _hoodieProduct.asStateFlow()

    init {
        fetchHoodieProduct()
    }

    private fun fetchHoodieProduct() {
        viewModelScope.launch {
            try {
                _hoodieProduct.emit(Resource.Loading())
                when (val result = hoodieUseCase.execute()) {
                    is Resource.Success -> {
                        _hoodieProduct.emit(result)
                        Log.d("HoodieViewModel", "fetchSpecialProducts:${result.data}")
                    }
                    is Resource.Error -> {
                        _hoodieProduct.emit(result)
                        Log.e("HoodieViewModel", "Error fetching all products: ${result.message}")
                    }
                    else-> Unit
                }
            } catch (e: Exception) {
                _hoodieProduct.emit(Resource.Error(e.message.toString()))
            }
        }
    }
}