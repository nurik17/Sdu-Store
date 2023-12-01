package com.example.sdustore.data.useCases

import com.example.sdustore.data.entity.MainRecyclerData
import com.example.sdustore.data.entity.Resource
import com.example.sdustore.data.repositories.MainRecyclerRepository

class MainRecyclerUseCaseInteraction(
    private val repository: MainRecyclerRepository
): MainRecyclerUseCase{
    override suspend fun execute(): Resource<List<MainRecyclerData>> {
        return repository.getNewsItems()
    }
}