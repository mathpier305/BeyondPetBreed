package com.example.beyondpetbreed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.beyondpetbreed.vm.PetBreedViewModel
import com.example.beyondpetbreed.repository.PetBreedRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.lang.Exception
import com.example.beyondpetbreed.util.Result
import kotlinx.coroutines.test.resumeDispatcher
import org.junit.*

class RepositoryTest {

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutinesApi  = MainCoroutineRule()

    private lateinit var petBreedRepository: PetBreedRepository
    private lateinit var viewModel : PetBreedViewModel

    @Before
    fun setup(){
        petBreedRepository = mock(PetBreedRepository::class.java)
        viewModel = PetBreedViewModel(petBreedRepository)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
       mainCoroutinesApi.resumeDispatcher()
    }

    @Test
    fun `test petBreed when first time open screen`() = runBlocking{
        val firstTimeSTate = viewModel.uiState.value as PetBreedUiState.NoDogBreeds
        firstTimeSTate.screenState
        Assert.assertFalse(firstTimeSTate.isLoading)
        Assert.assertEquals(firstTimeSTate.screenState, PetScreenState.FirstTime)
    }

    @Test
    fun `test Loading when fetchPetBreedByName is called `() = runBlocking {
        whenever(petBreedRepository.fetchPetBreed("hello")).thenReturn(Result.Error(Exception()))
        val uiStateBeforeFetch = viewModel.uiState.value as PetBreedUiState.NoDogBreeds
        Assert.assertEquals(uiStateBeforeFetch.screenState, PetScreenState.FirstTime)
        viewModel.fetchPetBreedByName("hello")
        val uiStateAfterFetch = viewModel.uiState.value as PetBreedUiState.NoDogBreeds
        Assert.assertEquals(uiStateAfterFetch.screenState, PetScreenState.Loading)
    }

}