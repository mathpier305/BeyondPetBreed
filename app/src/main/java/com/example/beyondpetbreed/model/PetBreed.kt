package com.example.beyondpetbreed

open  class Pet
data class PetBreed (
    val message: List<String>,
    val status: String
    ) : Pet()

data class PetBreedError(
    val message: String,
    val status: String,
    val code: Int
) : Pet()