package com.example.beyondpetbreed.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beyondpetbreed.util.Result
import com.example.beyondpetbreed.*
import com.example.beyondpetbreed.repository.PetBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetBreedViewModel @Inject constructor(
    private val repository: PetBreedRepository
) : ViewModel() {

    private val viewModelState = MutableStateFlow(PetBreedsViewModelState())
    val uiState = viewModelState
        .map { it.toUIState() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, viewModelState.value.toUIState())

    fun fetchPetBreedByName(name: String) {
        viewModelScope.launch {
            viewModelState.update { it.copy(isLoading = true, screenState = PetScreenState.Loading) }
            when(val result = repository.fetchPetBreed(name)) {
                is Result.Error -> {
                    viewModelState.update {
                        delay(4000)
                        it.copy(isLoading = false, screenState = PetScreenState.ErrorFound, errorMessage = result.exception.message ?:"Generic error")
                    }
                }
                is Result.Success -> {
                    viewModelState.update {
                        delay(4000)
                        val petBreedList : PetBreed = result.data as PetBreed
                        println("***** Result success: petBreedList is $petBreedList")
                        it.copy(isLoading = false, screenState = PetScreenState.BreedList, listOfPetBreeds = petBreedList.message)
                    }
                }
                is Result.ValidError -> {
                    viewModelState.update {
                        delay(4000)
                        val petBreed = result.data as PetBreedError
                        it.copy(
                            isLoading = false,
                            screenState = PetScreenState.ErrorFound,
                            errorMessage = petBreed.message,
                            errorCode = petBreed.code
                        )
                    }
                }
            }
        }
    }
}