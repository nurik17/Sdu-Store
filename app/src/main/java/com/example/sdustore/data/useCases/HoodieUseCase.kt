package com.example.sdustore.data.useCases

import com.example.sdustore.data.entity.Product
import com.example.sdustore.data.entity.Resource

interface HoodieUseCase {

    suspend fun execute(): Resource<List<Product>>
}