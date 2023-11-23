package com.example.sdustore.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdustore.data.MainRecyclerData
import com.example.sdustore.data.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore
) : ViewModel() {

    private val _newsAdapterItems =
        MutableStateFlow<Resource<List<MainRecyclerData>>>(Resource.UnSpecified())
    val newsAdapterItems = _newsAdapterItems.asStateFlow()

    init {
        fetchSpecialProducts()
    }

     private fun fetchSpecialProducts() {
        viewModelScope.launch {
            _newsAdapterItems.emit(Resource.Loading())
        }
        fireStore.collection("mainRecyclerData")
            .get()
            .addOnSuccessListener { result ->
                val dataList = result.toObjects(MainRecyclerData::class.java)
                viewModelScope.launch {
                    _newsAdapterItems.emit(Resource.Success(dataList))
                    Log.d("HomeViewModel", "fetchSpecialProducts:$dataList")
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _newsAdapterItems.emit(Resource.Error(it.message.toString()))
                }
            }
    }
}