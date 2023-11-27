package com.example.sdustore.ui.category.hoodie

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
import javax.inject.Inject


@HiltViewModel
class HoodieViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel(){

    private val _hoodieProduct = MutableStateFlow<Resource<List<Product>>>(Resource.UnSpecified())
    val hoodieProduct = _hoodieProduct.asStateFlow()

    init {
        fetchHoodieProduct()
    }

    private fun fetchHoodieProduct() {
        viewModelScope.launch {
            try{
                _hoodieProduct.emit(Resource.Loading())
                val result = fireStore.collection("Product")
                    .whereEqualTo("category","Hoodie")
                    .get()
                    .await()
                val dataList = result.toObjects(Product::class.java)
                _hoodieProduct.emit(Resource.Success(dataList))
            }catch (e: Exception){
                _hoodieProduct.emit(Resource.Error(e.message.toString()))
            }
        }
    }
}