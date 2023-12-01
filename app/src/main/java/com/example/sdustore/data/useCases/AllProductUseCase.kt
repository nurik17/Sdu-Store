package com.example.sdustore.data.useCases

import com.example.sdustore.data.entity.Product
import com.example.sdustore.data.entity.Resource


interface AllProductUseCase {
    suspend fun execute(): Resource<List<Product>>
}
