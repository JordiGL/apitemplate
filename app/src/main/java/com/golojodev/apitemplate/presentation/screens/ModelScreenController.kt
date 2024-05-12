package com.golojodev.apitemplate.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.golojodev.apitemplate.domain.models.Model
import com.golojodev.apitemplate.presentation.navigation.ContentType
import com.golojodev.apitemplate.presentation.screens.content.ModelList
import com.golojodev.apitemplate.presentation.screens.content.ModelListAndDetails
import com.golojodev.apitemplate.presentation.states.UIState

@Composable
fun ModelScreenController(
    modifier: Modifier,
    contentType: ContentType,
    uiState: UIState,
    onModelClicked: (Model) -> Unit
) {

    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = uiState.isLoading
        ) {
            CircularProgressIndicator()
        }
        AnimatedVisibility(
            visible = uiState.models.isNotEmpty()
        ) {
            if (contentType == ContentType.List) {
                ModelList(
                    onClicked = onModelClicked,
                    models = uiState.models,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                ModelListAndDetails(models = uiState.models)
            }
        }
        AnimatedVisibility(
            visible = uiState.error != null
        ) {
            Text(text = uiState.error ?: "")
        }
    }
}