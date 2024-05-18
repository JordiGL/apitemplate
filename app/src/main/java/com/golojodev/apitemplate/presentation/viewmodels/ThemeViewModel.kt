package com.golojodev.apitemplate.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.golojodev.apitemplate.domain.states.NetworkResult
import com.golojodev.apitemplate.domain.states.asResult
import com.golojodev.apitemplate.domain.usecases.UseCaseProvider
import com.golojodev.library.style.ThemeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel del mode del tema de l'APP
 */
class ThemeViewModel(
    private val useCaseProvider: UseCaseProvider
) : ViewModel() {

    val themeState = MutableStateFlow(ThemeState.DEFAULT)

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseProvider.onGetTheme().asResult().collect { result ->
                when (result) {
                    is NetworkResult.Error -> themeState.update { ThemeState.DEFAULT }
                    is NetworkResult.Success -> {themeState.update { result.data }}
                }
            }
        }
    }

    fun setTheme(value: ThemeState) {
        viewModelScope.launch(Dispatchers.IO) {
            themeState.update { value }
            useCaseProvider.onSaveTheme(value)
        }
    }
}