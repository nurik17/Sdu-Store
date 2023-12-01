package com.example.sdustore.data.useCases

import com.example.sdustore.data.entity.Product
import com.example.sdustore.data.entity.Resource
import com.example.sdustore.data.repositories.ProductRepository


class AllProductUseCaseInteraction(
    private val productRepo: ProductRepository
) : AllProductUseCase {
    override suspend fun execute(): Resource<List<Product>> {
        return productRepo.getAllProducts()
    }
}