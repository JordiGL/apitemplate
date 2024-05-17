package com.golojodev.library.style

import androidx.compose.runtime.State

/**
 * És responsable de gestionar l'estat del tema de l'aplicació.
 */
object ThemeStateManager {
    private lateinit var state: State<ThemeState>
    private var isDark: Boolean = false

    /**
     * Inicialitza l'estat del tema de l'aplicació.
     *
     * @param state Estat del tema mitjançant un objecte State de Jetpack Compose.
     * @param isDark Indica si el tema inicial ha de ser fosc (true) o clar (false).
     */
    fun init(
        state: State<ThemeState>,
        isDark: Boolean
    ) {
        ThemeStateManager.isDark = isDark
        ThemeStateManager.state = state
    }

    /**
     * Comprova si el tema actual de l'aplicació és fosc.
     *
     * @return True si el tema és fosc, False altrament.
     */
    fun isDark(): Boolean {
        return when (state.value) {
            ThemeState.DARK -> true
            ThemeState.LIGHT -> false
            ThemeState.DEFAULT -> isDark
        }
    }

    /**
     * Alterna entre el tema fosc i clar de l'aplicació.
     *
     * @param updateThemeState Funció opcional que s'executa quan s'actualitza l'estat del tema.
     * Rep com a paràmetre el nou estat del tema (ThemeState).
     */
    fun toggle(updateThemeState: (ThemeState) -> Unit = {}) {
        when (state.value) {
            ThemeState.DARK -> updateThemeState(ThemeState.LIGHT)
            ThemeState.LIGHT -> updateThemeState(ThemeState.DARK)
            ThemeState.DEFAULT -> when (isDark) {
                true -> updateThemeState(ThemeState.LIGHT)
                false -> updateThemeState(ThemeState.DARK)
            }
        }
    }
}