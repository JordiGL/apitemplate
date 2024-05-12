package com.golojodev.apitemplate.domain.repositories

import com.golojodev.apitemplate.domain.models.Model
import com.golojodev.apitemplate.domain.states.NetworkResult

/**
 * Fa d'enllaç entre el ViewModel i el servei de la API
 *
 * @author golojodev
 */
interface ModelRepository {
    suspend fun getModels(): NetworkResult<List<Model>>
}