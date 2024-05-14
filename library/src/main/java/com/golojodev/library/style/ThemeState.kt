package com.golojodev.library.style

import kotlinx.serialization.Serializable

/**
 * Classe per a definir les opcions que defineixen el mode del tema de l'APP
 */
@Serializable
enum class ThemeState {
    DARK,
    LIGHT,
    DEFAULT
}