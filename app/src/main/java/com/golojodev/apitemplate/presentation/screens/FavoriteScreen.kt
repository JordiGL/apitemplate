package com.golojodev.apitemplate.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.golojodev.apitemplate.domain.models.Model
import com.golojodev.apitemplate.presentation.screens.content.ModelListItem
import com.golojodev.apitemplate.presentation.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(
    onClicked: (Model) -> Unit
) {
    val mainViewModel: MainViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        mainViewModel.getFavorites()
    }
    val models by mainViewModel.favorites.collectAsStateWithLifecycle()

    if (models.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No favorite pets")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(models) { model ->
                ModelListItem(
                    model = model,
                    onClicked = onClicked,
                    onFavoriteClicked = {
                        mainViewModel.update(it)
                    }
                )
            }
        }
    }
}