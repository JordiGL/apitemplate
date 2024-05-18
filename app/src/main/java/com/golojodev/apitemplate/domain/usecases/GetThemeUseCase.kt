package com.golojodev.apitemplate.domain.usecases

import com.golojodev.apitemplate.domain.repositories.ThemeRepository

/**
 * UseCase per obtenir el tema de l'APP
 *
 * @param themeRepository [ThemeRepository]
 */
class GetThemeUseCase (
    private val themeRepository: ThemeRepository
){
    suspend operator fun invoke() = themeRepository.getTheme()
}