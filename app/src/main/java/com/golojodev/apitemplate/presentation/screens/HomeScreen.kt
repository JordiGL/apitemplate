package com.golojodev.apitemplate.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.golojodev.apitemplate.domain.models.Model
import com.golojodev.apitemplate.presentation.navigation.ContentType
import com.golojodev.apitemplate.presentation.viewmodels.ModelViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(contentType: ContentType, onModelClicked: (Model) -> Unit = {}) {
    val modelViewModel: ModelViewModel = koinViewModel()
    val uiState by modelViewModel.uiState.collectAsStateWithLifecycle()
    ModelScreenController(
        modifier = Modifier.fillMaxSize(),
        contentType = contentType,
        uiState = uiState,
        onModelClicked = onModelClicked
    )
}