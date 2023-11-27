package com.example.sdustore.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdustore.data.MainRecyclerData
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
class HomeViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val storage: FirebaseStorage
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
                val result = fireStore.collection("mainRecyclerData")
                    .get()
                    .await()
                val dataList = result.toObjects(MainRecyclerData::class.java)
                _newsAdapterItems.emit(Resource.Success(dataList))
                Log.d("HomeViewModel", "fetchSpecialProducts:$dataList")
            } catch (e: Exception) {
                _newsAdapterItems.emit(Resource.Error(e.message.toString()))
            }
        }
    }

}