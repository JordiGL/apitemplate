package com.golojodev.apitemplate.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "Model")
data class ModelEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val tags: List<String>,
    @ColumnInfo(defaultValue = "0")
    val isFavorite: Boolean = false
)