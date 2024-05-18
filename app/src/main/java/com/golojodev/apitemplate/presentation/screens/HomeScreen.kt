package com.golojodev.apitemplate.presentation.screens

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.golojodev.apitemplate.domain.models.Model
import com.golojodev.apitemplate.presentation.components.PermissionDialog
import com.golojodev.apitemplate.presentation.navigation.ContentType
import com.golojodev.apitemplate.presentation.viewmodels.MainViewModel
import com.golojodev.library.permission.PermissionAction
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    contentType: ContentType,
    onModelClicked: (Model) -> Unit = {}
) {
    val mainViewModel: MainViewModel = koinViewModel()
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showContent by rememberSaveable { mutableStateOf(false) }

    PermissionDialog(
        context = context,
        permission = Manifest.permission.ACCESS_COARSE_LOCATION
    ) { permissionAction ->
        showContent = when (permissionAction) {
            is PermissionAction.PermissionDenied -> {
                false
            }

            is PermissionAction.PermissionGranted -> {
                true
            }
        }
    }
    if (showContent) {
        ModelScreenController(
            modifier = Modifier.fillMaxSize(),
            contentType = contentType,
            uiState = uiState,
            onFavoriteClicked = {
                mainViewModel.update(it)
            },
            onModelClicked = onModelClicked
        )
    }
}