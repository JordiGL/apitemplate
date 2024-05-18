package com.golojodev.apitemplate.domain.usecases

import com.golojodev.apitemplate.domain.repositories.ThemeRepository
import com.golojodev.library.style.ThemeState

/**
 * UseCase per recuperar el [ThemeState]
 *
 * @param themeRepository [ThemeRepository]
 */
class SaveThemeUseCase(
    private val themeRepository: ThemeRepository
) {
    suspend operator fun invoke(themeState: ThemeState) {
        themeRepository.saveTheme(themeState)
    }
}