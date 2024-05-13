package com.golojodev.apitemplate.presentation.screens.content

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.golojodev.apitemplate.domain.models.Model

@Composable
fun ModelList(
    modifier: Modifier,
    models: List<Model>,
    onFavoriteClicked: (Model) -> Unit,
    onClicked: (Model) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(models) { model ->
            ModelListItem(
                model = model,
                onFavoriteClicked = onFavoriteClicked,
                onClicked = onClicked
            )
        }
    }
}