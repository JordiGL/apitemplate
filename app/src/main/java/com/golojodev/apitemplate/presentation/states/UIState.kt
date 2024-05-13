package com.golojodev.apitemplate.presentation.states

import com.golojodev.apitemplate.domain.models.Model

data class UIState(
    val isLoading: Boolean = false,
    val models: List<Model> = emptyList(),
    val error: String? = null
)