package com.golojodev.apitemplate.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ThemeStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(themeStateEntity: ThemeStateEntity)

    @Update
    suspend fun update(themeStateEntity: ThemeStateEntity)

    @Query("SELECT * FROM ThemeState")
    fun getThemeState(): Flow<ThemeStateEntity>
}