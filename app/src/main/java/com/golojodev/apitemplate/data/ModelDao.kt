package com.golojodev.apitemplate.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(modelEntity: ModelEntity)

    @Query("SELECT * FROM Model")
    fun getModels(): Flow<List<ModelEntity>>

    @Update
    suspend fun update(modelEntity: ModelEntity)

    @Query("SELECT * FROM Model WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<ModelEntity>>
}