package com.example.beyondpetbreed.repository

import com.example.beyondpetbreed.Pet
import com.example.beyondpetbreed.util.Result

interface PetBreedRepository {

    suspend fun fetchPetBreed(name: String) : Result<Pet>
}