package com.golojodev.apitemplate.presentation.screens.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.golojodev.apitemplate.domain.models.Model

@Composable
fun ModelListAndDetails(models: List<Model>) {
    var currentModel by remember { mutableStateOf(models.first()) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ModelList(
            onClicked = {
                currentModel = it
            },
            models = models,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        DetailsScreenContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            model = currentModel
        )
    }
}