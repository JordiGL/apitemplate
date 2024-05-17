package com.golojodev.apitemplate.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ModelEntity::class, ThemeStateEntity::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 3, to = 4)
    ]
)
@TypeConverters(ModelsTypeConverters::class)
abstract class ModelDatabase : RoomDatabase() {
    abstract fun modelDao(): ModelDao

    abstract fun themeStateDao(): ThemeStateDao
}