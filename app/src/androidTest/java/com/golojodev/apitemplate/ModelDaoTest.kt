package com.golojodev.apitemplate

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.golojodev.apitemplate.data.ModelDao
import com.golojodev.apitemplate.data.ModelDatabase
import com.golojodev.apitemplate.data.ModelEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ModelDaoTest {
    private lateinit var database: ModelDatabase
    private lateinit var modelDao: ModelDao

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ModelDatabase::class.java
        ).allowMainThreadQueries().build()
        modelDao = database.modelDao()
    }

    @Test
    fun testInsertAndReadModel() = runTest {
        val model = ModelEntity(
            id = 1,
            name = "John Doe",
            tags = listOf("cute", "fluffy"),
            isFavorite = false
        )
        modelDao.insert(model)
        val models = modelDao.getModels()
        assert(models.first().contains(model))
    }

    @Test
    fun testAddCatToFavorites() = runTest {
        val model = ModelEntity(
            id = 1,
            name = "John Doe",
            tags = listOf("cute", "fluffy"),
            isFavorite = false
        )
        modelDao.insert(model)
        modelDao.update(model.copy(isFavorite = true))
        val favoriteCats = modelDao.getFavorites()
        assert(favoriteCats.first().contains(model.copy(isFavorite = true)))
    }

    @After
    fun closeDatabase() {
        database.close()
    }
}