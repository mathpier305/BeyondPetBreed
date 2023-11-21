package com.example.beyondpetbreed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.beyondpetbreed.ui.PetBreedContainer
import com.example.beyondpetbreed.vm.PetBreedViewModel
import com.example.beyondpetbreed.ui.theme.BeyondPetBreedTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val petBreadViewModel by viewModels<PetBreedViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeyondPetBreedTheme {
                val uiState = petBreadViewModel.uiState.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PetBreedContainer(
                        uiState = uiState.value,
                        onSearchButtonClick = {
                            petBreadViewModel.fetchPetBreedByName(it)
                        }
                    )
                }
            }
        }
    }
}
