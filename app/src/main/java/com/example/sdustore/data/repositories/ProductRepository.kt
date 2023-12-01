package com.example.sdustore.data.repositories

import com.example.sdustore.data.entity.Product
import com.example.sdustore.data.entity.Resource

interface ProductRepository {
    suspend fun getAllProducts(): Resource<List<Product>>
    suspend fun getHoodie(): Resource<List<Product>>
}
