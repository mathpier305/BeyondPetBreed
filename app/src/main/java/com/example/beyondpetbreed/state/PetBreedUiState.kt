package com.example.beyondpetbreed

enum class PetScreenState {
    Loading, FirstTime, ErrorFound, BreedList
}

sealed interface PetBreedUiState {

    val screenState: PetScreenState

    data class NoDogBreeds(
        val isLoading: Boolean,
        val errorMessage: String,
        val code: Int,
        override val screenState: PetScreenState
    ) : PetBreedUiState

    data class FoundDogBreeds(
        val listOfPetBreeds : List<String>,
        override val screenState: PetScreenState
    ): PetBreedUiState
}

data class PetBreedsViewModelState(
    val screenState: PetScreenState = PetScreenState.FirstTime,
    val isLoading: Boolean = false,
    val errorMessage: String ="",
    val errorCode: Int =  0,
    val listOfPetBreeds: List<String> = emptyList(),
) {
   fun  toUIState(): PetBreedUiState {
       return if(screenState != PetScreenState.BreedList) {
           PetBreedUiState.NoDogBreeds(
               isLoading = isLoading,
               errorMessage = errorMessage,
               screenState = screenState,
               code = errorCode
           )
       } else{
           PetBreedUiState.FoundDogBreeds(
               listOfPetBreeds = listOfPetBreeds,
               screenState = screenState
           )
       }
   }
}