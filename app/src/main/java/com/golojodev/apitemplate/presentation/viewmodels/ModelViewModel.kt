package com.golojodev.apitemplate.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.golojodev.apitemplate.domain.models.Model
import com.golojodev.apitemplate.domain.repositories.ModelRepository
import com.golojodev.apitemplate.domain.states.NetworkResult
import com.golojodev.apitemplate.domain.states.asResult
import com.golojodev.apitemplate.presentation.states.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Gestiona les dades a mostrar a la vista (ui)
 */
class ModelViewModel(
    private val modelRepository: ModelRepository
) : ViewModel() {
    val uiState = MutableStateFlow(UIState())
    private val _favorites = MutableStateFlow<List<Model>>(emptyList())
    val favorites: StateFlow<List<Model>> get() = _favorites

    init {
        getModels()
    }

    fun getModels() {
        uiState.value = UIState(isLoading = true)
        viewModelScope.launch {
            modelRepository.getModels().asResult().collect { result ->
                when (result) {
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

    fun update(model: Model) {
        viewModelScope.launch {
            modelRepository.updateModel(model)
        }
    }
    fun getFavorites() {
        viewModelScope.launch {
            modelRepository.getFavorites().collect {
                _favorites.value = it
            }
        }
    }
}