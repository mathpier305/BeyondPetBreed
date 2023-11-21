package com.example.beyondpetbreed.retrofit


import com.example.beyondpetbreed.PetBreed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("breed/{name}/images")
    suspend fun getDogByBreeds(@Path("name") name : String) : Response<PetBreed>
}