package com.example.sdustore.data.useCases

import com.example.sdustore.data.entity.MainRecyclerData
import com.example.sdustore.data.entity.Resource

interface MainRecyclerUseCase{
    suspend fun execute(): Resource<List<MainRecyclerData>>
}