package com.golojodev.apitemplate.domain.usecases

import com.golojodev.apitemplate.domain.repositories.ModelRepository

class GetFavoritesUseCase(
    private val modelRepository: ModelRepository
) {
    suspend operator fun invoke() = modelRepository.getFavorites()
}