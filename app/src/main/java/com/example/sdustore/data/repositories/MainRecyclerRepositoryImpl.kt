package com.example.sdustore.data.repositories

import com.example.sdustore.data.entity.MainRecyclerData
import com.example.sdustore.data.entity.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MainRecyclerRepositoryImpl(private val fireStore: FirebaseFirestore): MainRecyclerRepository {
    override suspend fun getNewsItems(): Resource<List<MainRecyclerData>> {
        return try{
            val result = fireStore.collection("mainRecyclerData")
                .get()
                .await()
            val dataList = result.toObjects(MainRecyclerData::class.java)
            Resource.Success(dataList)
        }catch (e: Exception){
            Resource.Error(e.message.toString())
        }
    }
}