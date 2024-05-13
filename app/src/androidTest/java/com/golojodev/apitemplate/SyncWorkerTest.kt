package com.golojodev.apitemplate

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.golojodev.apitemplate.data.workers.ModelsSyncWorker
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SyncWorkerTest {
    @get:Rule
    val koinTestRule = KoinTestRule()

    @Before
    fun setUp() {
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()
        // Inicializa el WorkManager para crear los tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(
            ApplicationProvider.getApplicationContext(),
            config
        )
    }

    @Test
    fun testModelsSyncWorker() {
        val syncPetsWorkRequest = OneTimeWorkRequestBuilder<ModelsSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()
        val workManager = WorkManager.getInstance(ApplicationProvider.getApplicationContext())
        val testDriver =
            WorkManagerTestInitHelper.getTestDriver(ApplicationProvider.getApplicationContext())!!

        workManager.enqueueUniqueWork(
            "ModelsSyncWorker",
            ExistingWorkPolicy.KEEP,
            syncPetsWorkRequest
        ).result.get()

        val workInfo = workManager.getWorkInfoById(syncPetsWorkRequest.id).get()

        TestCase.assertEquals(WorkInfo.State.ENQUEUED, workInfo.state)

        // El dispositivo simulara en el dispositivo las constraints determinadas en syncPetsWorkRequest
        // para que se inicie el processo que tenemos en espera(enqueueUniqueWork).
        testDriver.setAllConstraintsMet(syncPetsWorkRequest.id)

        val postRequirementWorkInfo = workManager.getWorkInfoById(syncPetsWorkRequest.id).get()
        TestCase.assertEquals(WorkInfo.State.RUNNING, postRequirementWorkInfo.state)
    }
}