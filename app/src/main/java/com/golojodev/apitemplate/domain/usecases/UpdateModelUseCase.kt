package com.golojodev.apitemplate.domain.usecases

import com.golojodev.apitemplate.domain.models.Model
import com.golojodev.apitemplate.domain.repositories.ModelRepository

/**
 * Actualitza el model a la base de dades
 *
 * @param modelRepository [ModelRepository]
 */
class UpdateModelUseCase(
    private val modelRepository: ModelRepository
) {
    suspend operator fun invoke(model: Model) {
        modelRepository.updateModel(model)
    }
}