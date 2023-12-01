package com.example.sdustore.data.repositories

import com.example.sdustore.data.entity.MainRecyclerData
import com.example.sdustore.data.entity.Resource

interface MainRecyclerRepository {
    suspend fun getNewsItems(): Resource<List<MainRecyclerData>>
}