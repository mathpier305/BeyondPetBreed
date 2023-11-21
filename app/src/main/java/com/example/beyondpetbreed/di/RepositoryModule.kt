package com.example.beyondpetbreed.di

import com.example.beyondpetbreed.retrofit.ApiService
import com.example.beyondpetbreed.repository.PetBreedRepository
import com.example.beyondpetbreed.repository.PetBreedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesPetBreedRepository(apiService: ApiService) : PetBreedRepository {
        return PetBreedRepositoryImpl(apiService)
    }
}