package com.golojodev.apitemplate.domain.repositories

import com.golojodev.apitemplate.data.service.ServiceAPI
import com.golojodev.apitemplate.domain.models.Model
import com.golojodev.apitemplate.domain.states.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

class ModelRepositoryImpl(
    private val serviceAPI: ServiceAPI,
    private val dispatcher: CoroutineDispatcher
) : ModelRepository {
    override suspend fun getModels(): NetworkResult<List<Model>> {
        return withContext(dispatcher) {
            try {
                //val response = serviceAPI.fetchData("tag")
                val response : Response<List<Model>> = Response.success(
                    listOf(
                        Model(1, "Model 1"),
                        Model(2, "Model 2")
                    )
                )
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(response.errorBody().toString())
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}