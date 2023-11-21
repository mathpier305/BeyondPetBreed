package com.example.beyondpetbreed.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.beyondpetbreed.PetBreedUiState
import com.example.beyondpetbreed.PetScreenState
import com.example.beyondpetbreed.R


@Composable
fun PetBreedContainer(uiState: PetBreedUiState, onSearchButtonClick: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        EnterBreedTextField(onSearchButtonClick)
        when(uiState.screenState) {
            PetScreenState.Loading -> {
                PetBreedLoading()
            }
            PetScreenState.ErrorFound -> {
                val noDogBreedsState  : PetBreedUiState.NoDogBreeds = uiState as PetBreedUiState.NoDogBreeds
                PetBreedError(noDogBreedsState)
            }
            PetScreenState.FirstTime -> {
                PetBreedInitialState()
            }
            else -> {
                val breedList : PetBreedUiState.FoundDogBreeds = uiState as PetBreedUiState.FoundDogBreeds
                PetBreedList(listOfBreed = breedList.listOfPetBreeds )
            }
        }
    }
}

@Composable
fun PetBreedInitialState() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 48.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(textAlign = TextAlign.Center, text = stringResource(id = R.string.search_dog_by_breed), fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        Image(painter = painterResource(id = R.drawable.dog_api_logo), contentDescription = "dog picture")
    }
}

@Composable
fun PetBreedList(listOfBreed: List<String>) {
    GridCells.Fixed(2)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(listOfBreed) { index->
            AsyncImage(model = index, contentScale = ContentScale.FillBounds, contentDescription = "")
        }
    }
}
@Composable
fun PetBreedLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(modifier =Modifier.width(100.dp) )
        Spacer(modifier = Modifier.height(100.dp))
        Text(text= stringResource(id = R.string.loading), fontSize = 24.sp)
    }
}


@Composable
fun EnterBreedTextField(onSearchButtonClick : (String) -> Unit) {
    var textFieldValue by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
        label = {
            Text(text = stringResource(id = R.string.breed_name))
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Star, contentDescription = "")
        },
        trailingIcon = {
            Button(onClick = {
                onSearchButtonClick(textFieldValue)
            }) {
                Text(text = stringResource(id = R.string.search))
            }
        }
    )
}

@Composable
fun PetBreedError(state: PetBreedUiState.NoDogBreeds) {
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(horizontal = 48.dp), verticalArrangement = Arrangement.Center) {
        Text(textAlign = TextAlign.Center, text ="${state.errorMessage} \n (Code ${state.code} )", fontSize = 24.sp, color = Color.Red)
    }
}


@Preview
@Composable
fun PreviewPetBreedInitialState() {
    PetBreedInitialState()
}
