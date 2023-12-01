package com.example.sdustore.data.modules

import com.example.sdustore.data.repositories.MainRecyclerRepository
import com.example.sdustore.data.repositories.ProductRepository
import com.example.sdustore.data.useCases.AllProductUseCase
import com.example.sdustore.data.useCases.AllProductUseCaseInteraction
import com.example.sdustore.data.useCases.HoodieUseCase
import com.example.sdustore.data.useCases.HoodieUseCaseInteraction
import com.example.sdustore.data.useCases.MainRecyclerUseCase
import com.example.sdustore.data.useCases.MainRecyclerUseCaseInteraction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Provides
    fun provideProductUseCase(repo: ProductRepository): AllProductUseCase =
        AllProductUseCaseInteraction(repo)

    @Provides
    fun provideHoodieUseCase(repo: ProductRepository): HoodieUseCase =
        HoodieUseCaseInteraction(repo)

    @Provides
    fun provideMainRecyclerUseCase(repo: MainRecyclerRepository): MainRecyclerUseCase =
        MainRecyclerUseCaseInteraction(repo)
}