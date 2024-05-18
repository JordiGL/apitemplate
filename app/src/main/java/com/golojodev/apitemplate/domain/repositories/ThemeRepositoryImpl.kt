package com.golojodev.apitemplate.domain.repositories

import android.util.Log
import com.golojodev.apitemplate.data.ThemeStateDao
import com.golojodev.apitemplate.data.ThemeStateEntity
import com.golojodev.library.style.ThemeState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ThemeRepositoryImpl(
    private val themeDao: ThemeStateDao,
    private val dispatcher: CoroutineDispatcher
) : ThemeRepository {
    override suspend fun getTheme(): Flow<ThemeState> {
        return withContext(dispatcher) {
            themeDao.getThemeState().map {
                it.theme
            }
        }
    }

    override suspend fun saveTheme(themeState: ThemeState) {
        withContext(dispatcher) {
            Log.i("THEME", Json.encodeToString(themeState))
            themeDao.update(ThemeStateEntity(theme = themeState))
        }
    }
}