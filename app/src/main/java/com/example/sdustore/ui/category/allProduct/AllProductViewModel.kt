package com.example.sdustore.ui.category.allProduct

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdustore.data.Product
import com.example.sdustore.data.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AllProductViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel(){

    private val _allProducts = MutableStateFlow<Resource<List<Product>>>(Resource.UnSpecified())
    val allProducts = _allProducts.asStateFlow()

    init {
        fetchAllProducts()
    }

    private fun fetchAllProducts(){
        viewModelScope.launch {
            try{
                _allProducts.emit(Resource.Loading())
                val result = fireStore.collection("Product")
                    .whereEqualTo("category","Hoodie")
                    .get()
                    .await()
                val dataList = result.toObjects(Product::class.java)
                _allProducts.emit(Resource.Success(dataList))
                Log.d("AllProductViewModel", "fetchSpecialProducts:$dataList")
            }catch (e: Exception){
                _allProducts.emit(Resource.Error(e.message.toString()))
            }
        }
    }
}