package com.example.sdustore.data.repositories

import com.example.sdustore.data.entity.Product
import com.example.sdustore.data.entity.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
): ProductRepository{
    override suspend fun getAllProducts(): Resource<List<Product>> {
        return try {
            val result = fireStore.collection("Product")
                .whereEqualTo("category","All")
                .get()
                .await()
            val dataList = result.toObjects(Product::class.java)
            Resource.Success(dataList)
        }catch (e : Exception){
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun getHoodie(): Resource<List<Product>> {
        return try {
            val result = fireStore.collection("Product")
                .whereEqualTo("category","Hoodie")
                .get()
                .await()
            val dataList = result.toObjects(Product::class.java)
            Resource.Success(dataList)
        }catch (e : Exception){
            Resource.Error(e.message.toString())
        }
    }
}