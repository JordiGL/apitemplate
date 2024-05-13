package com.golojodev.apitemplate

import com.golojodev.apitemplate.domain.models.Model
import com.golojodev.apitemplate.domain.repositories.ModelRepository
import com.golojodev.apitemplate.presentation.viewmodels.ModelViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ModelsViewModelTest {
    private val modelRepository = mockk<ModelRepository>(relaxed = true)
    private lateinit var modelViewModel: ModelViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        modelViewModel = ModelViewModel(modelRepository)
    }

    @Test
    fun testGetModels() = runTest {
        val models = listOf(
            Model(
                id = 1,
                name = "John Doe",
                tags = listOf("cute", "fluffy"),
                isFavorite = false
            )
        )

        coEvery { modelRepository.getModels() } returns flowOf(models)
        modelViewModel.getModels()
        coVerify { modelRepository.getModels() }
        val uiState = modelViewModel.uiState.value
        assertEquals(models, uiState.models)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}