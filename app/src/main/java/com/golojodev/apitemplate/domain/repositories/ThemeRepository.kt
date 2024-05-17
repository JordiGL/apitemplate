package com.golojodev.apitemplate.domain.repositories

import com.golojodev.library.style.ThemeState
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    suspend fun getTheme(): Flow<ThemeState>

    suspend fun saveTheme(themeState: ThemeState)
}