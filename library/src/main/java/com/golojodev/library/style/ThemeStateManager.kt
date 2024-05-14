package com.golojodev.library.style

import androidx.compose.runtime.State

/**
 * És responsable de gestionar l'estat del tema de l'aplicació.
 */
object ThemeStateManager {
    private lateinit var state: State<ThemeState>
    private var isDark: Boolean = false
    private var iconDark: Int? = null
    private var iconLight: Int? = null

    /**
     * Inicialitza l'estat del tema de l'aplicació.
     *
     * @param state Estat del tema mitjançant un objecte State de Jetpack Compose.
     * @param isDark Indica si el tema inicial ha de ser fosc (true) o clar (false).
     * @param iconDark ID del recurs drawable de l'icona per al mode fosc.
     * @param iconLight ID del recurs drawable de l'icona per al mode clar.
     */
    fun init(
        state: State<ThemeState>,
        isDark: Boolean,
        iconDark: Int,
        iconLight: Int
    ) {
        ThemeStateManager.isDark = isDark
        ThemeStateManager.state = state
        ThemeStateManager.iconDark = iconDark
        ThemeStateManager.iconLight = iconLight
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

    /**
     * Recupera la icona adequada en funció de l'estat del tema.
     *
     * @return ID del recurs drawable de la icona segons el tema (clar o fosc).
     */
    fun getIcon(): Int? {
        return when (state.value) {
            ThemeState.DARK -> iconLight
            ThemeState.LIGHT -> iconDark
            ThemeState.DEFAULT -> when (isDark) {
                true -> iconLight
                false -> iconDark
            }
        }
    }
}