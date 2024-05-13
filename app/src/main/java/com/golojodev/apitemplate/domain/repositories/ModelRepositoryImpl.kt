package com.golojodev.apitemplate.domain.repositories

import com.golojodev.apitemplate.data.ModelDao
import com.golojodev.apitemplate.data.ModelEntity
import com.golojodev.apitemplate.data.service.ServiceAPI
import com.golojodev.apitemplate.domain.models.Model
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import retrofit2.Response

class ModelRepositoryImpl(
    private val serviceAPI: ServiceAPI,
    private val dispatcher: CoroutineDispatcher,
    private val modelDao: ModelDao
) : ModelRepository {
    override suspend fun getModels(): Flow<List<Model>> {
        return withContext(dispatcher) {
            modelDao.getModels()
                .map { modelsCatched ->
                    modelsCatched.map { modelEntity ->
                        Model(
                            id = modelEntity.id,
                            name = modelEntity.name,
                            tags = modelEntity.tags,
                            isFavorite = modelEntity.isFavorite
                        )
                    }
                }
                .onEach {
                    if (it.isEmpty()) {
                        fetchRemoteModels()
                    }
                }
        }
    }

    override suspend fun fetchRemoteModels() {
        withContext(dispatcher) {
            // val response = serviceAPI.fetchData("cute")
            val response: Response<List<Model>> = Response.success(
                listOf(
                    Model(1, "Model 1", listOf("tag1", "tag2")),
                    Model(2, "Model 2", listOf("tag1", "tag2"))
                )
            )
            if (response.isSuccessful) {
                response.body()!!.map {
                    modelDao.insert(
                        ModelEntity(
                            id = it.id,
                            name = it.name,
                            tags = it.tags,
                            isFavorite = it.isFavorite
                        )
                    )
                }
            }
        }
    }

    override suspend fun updateModel(model: Model) {
        withContext(dispatcher) {
            modelDao.update(
                ModelEntity(
                    id = model.id,
                    name = model.name,
                    tags = model.tags,
                    isFavorite = model.isFavorite
                )
            )
        }
    }

    override suspend fun getFavorites(): Flow<List<Model>> {
        return withContext(dispatcher) {
            modelDao.getFavorites()
                .map { modelsCached ->
                    modelsCached.map { modelEntity ->
                        Model(
                            id = modelEntity.id,
                            name = modelEntity.name,
                            tags = modelEntity.tags,
                            isFavorite = modelEntity.isFavorite
                        )
                    }
                }
        }
    }
}