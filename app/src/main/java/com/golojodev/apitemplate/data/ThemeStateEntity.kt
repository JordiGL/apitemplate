package com.golojodev.apitemplate.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.golojodev.library.style.ThemeState

@Entity(tableName = "ThemeState")
data class ThemeStateEntity(
    @PrimaryKey
    @ColumnInfo(defaultValue = "0")
    val id: Int,
    @ColumnInfo(defaultValue = "DEFAULT")
    val theme: ThemeState
)