package com.golojodev.apitemplate.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.golojodev.apitemplate.domain.repositories.ModelRepository
import com.golojodev.apitemplate.domain.states.NetworkResult
import com.golojodev.apitemplate.presentation.states.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Gestiona les dades a mostrar a la vista (ui)
 */
class ModelViewModel(
    private val modelRepository: ModelRepository
) : ViewModel() {
    val uiState = MutableStateFlow(UIState())

    init {
        getModels()
    }

    fun getModels(){
        uiState.value = UIState(isLoading = true)
        viewModelScope.launch {
            when( val result = modelRepository.getModels()){
                is NetworkResult.Success -> {
                    uiState.update {
                        it.copy(isLoading = false, models = result.data)
                    }
                }
                is NetworkResult.Error -> {
                    uiState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }
}