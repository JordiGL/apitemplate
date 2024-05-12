package com.golojodev.apitemplate.domain.states

/**
 * Classe segellada que representa l'estat d'una crida a l'API.
 */
sealed class NetworkResult<out T> {

    /**
     * Classe de dades que representa una crida a l'API exitosa.
     *
     * @param data Les dades retornades per la crida a l'API.
     */
    data class Success<out T>(val data: T) : NetworkResult<T>()

    /**
     * Classe de dades que representa una crida a l'API fallida.
     *
     * @param error El missatge d'error retornat per la crida a l'API.
     */
    data class Error(val error: String) : NetworkResult<Nothing>()
}