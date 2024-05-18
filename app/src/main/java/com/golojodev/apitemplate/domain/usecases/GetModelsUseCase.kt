package com.golojodev.apitemplate.domain.usecases

import com.golojodev.apitemplate.domain.repositories.ModelRepository

/**
 * Obte les dades de la base de dades o la API si escau
 *
 * @param modelRepository [ModelRepository]
 */
class GetModelsUseCase(
    private val modelRepository: ModelRepository
) {
    suspend operator fun invoke() = modelRepository.getModels()
}