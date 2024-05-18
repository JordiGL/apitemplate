package com.golojodev.apitemplate.domain.usecases

import com.golojodev.apitemplate.domain.repositories.ModelRepository

/**
 * Obte les dades de la API
 */
class FetchModelsUseCase(
    private val modelRepository: ModelRepository
) {
    suspend operator fun invoke() = modelRepository.fetchRemoteModels()
}