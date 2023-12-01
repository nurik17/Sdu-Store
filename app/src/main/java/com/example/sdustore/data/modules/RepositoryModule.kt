package com.example.sdustore.data.modules

import com.example.sdustore.data.repositories.MainRecyclerRepository
import com.example.sdustore.data.repositories.MainRecyclerRepositoryImpl
import com.example.sdustore.data.repositories.ProductRepository
import com.example.sdustore.data.repositories.ProductRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepository(fireStore: FirebaseFirestore): ProductRepository =
        ProductRepositoryImpl(fireStore)

    @Singleton
    @Provides
    fun provideMainRecyclerRepository(fireStore: FirebaseFirestore): MainRecyclerRepository =
        MainRecyclerRepositoryImpl(fireStore)
}