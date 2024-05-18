package com.golojodev.apitemplate.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.golojodev.apitemplate.domain.models.Model
import com.golojodev.apitemplate.domain.states.NetworkResult
import com.golojodev.apitemplate.domain.states.asResult
import com.golojodev.apitemplate.domain.usecases.UseCaseProvider
import com.golojodev.apitemplate.presentation.states.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Gestiona les dades a mostrar a la vista (ui)
 */
class MainViewModel(
    private val useCaseProvider: UseCaseProvider
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
            useCaseProvider.onGetModels().asResult().collect { result ->
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

    fun fetchRemoteModels() {
        uiState.value = UIState(isLoading = true)
        viewModelScope.launch {
            useCaseProvider.onFetchModels().asResult().collect { result ->
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
           useCaseProvider.onUpdateModel(model)
        }
    }
    fun getFavorites() {
        viewModelScope.launch {
            useCaseProvider.onGetFavorites().collect { result ->
                _favorites.value = result
            }
        }
    }
}