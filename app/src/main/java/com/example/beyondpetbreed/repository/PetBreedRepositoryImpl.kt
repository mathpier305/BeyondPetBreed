package com.example.beyondpetbreed.repository


import com.example.beyondpetbreed.Pet
import com.example.beyondpetbreed.PetBreedError
import com.example.beyondpetbreed.util.Result
import com.example.beyondpetbreed.retrofit.ApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class PetBreedRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    PetBreedRepository {

    override suspend fun fetchPetBreed(name: String): Result<Pet> {
        val response = apiService.getDogByBreeds(name)
       return  if(response.isSuccessful) {
            val petBreedResult = response.body()
            if(petBreedResult != null) {
                Result.Success(petBreedResult)
            } else {
                Result.Error(IllegalStateException("Response is successful yet failed"))
            }
        } else {
            val gson = Gson()
           val type = object : TypeToken<PetBreedError>() {}.type
           val  errorResponse: PetBreedError? = gson.fromJson(response.errorBody()!!.charStream(), type)
            if(errorResponse != null) {
                Result.ValidError(errorResponse)
            } else {
                Result.Error(IllegalStateException("Response is not successful"))
            }
        }
    }
}