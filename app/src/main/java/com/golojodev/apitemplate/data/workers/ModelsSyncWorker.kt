package com.golojodev.apitemplate.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.golojodev.apitemplate.domain.repositories.ModelRepository

class ModelsSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val modelRepository: ModelRepository
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            modelRepository.fetchRemoteModels()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}