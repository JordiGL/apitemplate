package com.golojodev.apitemplate.presentation.screens.content

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.golojodev.apitemplate.domain.models.Model

@Composable
fun ModelList(
    onClicked: (Model) -> Unit,
    models: List<Model>,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(models) { model ->
            ModelListItem(
                model = model,
                onClicked = onClicked
            )
        }
    }
}