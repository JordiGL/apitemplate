package com.golojodev.apitemplate.presentation.screens

import androidx.compose.runtime.Composable
import com.golojodev.apitemplate.presentation.screens.content.SettingsScreenContent

@Composable
fun SettingsScreen(
    onThemeChange: () -> Unit = {}
) {
    SettingsScreenContent {
        onThemeChange()
    }
}