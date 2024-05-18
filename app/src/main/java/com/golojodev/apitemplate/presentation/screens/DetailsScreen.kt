package com.golojodev.apitemplate.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.golojodev.apitemplate.domain.models.Model
import com.golojodev.apitemplate.presentation.screens.content.DetailsScreenContent

@Composable
fun DetailsScreen(model: Model) {
    Scaffold(
        content = { paddingValues ->
            DetailsScreenContent(
                modifier = Modifier.padding(paddingValues),
                model = model
            )
        }
    )
}