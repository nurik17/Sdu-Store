package com.example.sdustore.data.useCases

import com.example.sdustore.data.entity.Product
import com.example.sdustore.data.entity.Resource
import com.example.sdustore.data.repositories.ProductRepository

class HoodieUseCaseInteraction(
    private val productRepo: ProductRepository
) : HoodieUseCase {
    override suspend fun execute(): Resource<List<Product>> {
        return productRepo.getHoodie()
    }
}