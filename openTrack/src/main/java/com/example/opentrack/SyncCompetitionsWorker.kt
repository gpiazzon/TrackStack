package com.example.opentrack

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.data.Repository
import com.example.data.Settings
import java.util.concurrent.TimeUnit

class SyncCompetitionsWorker(
    appContext: Context,
    params: WorkerParameters,
    private val settings: Settings,
    private val service: OpenTrackService,
    private val repository: Repository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            val id = settings.athleteId
            val competitions = service.getCompetitions(id)
            repository.upsertCompetitions(competitions)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        private const val WORK_NAME = "sync_competitions"

        fun schedule(context: Context) {
            val request = PeriodicWorkRequestBuilder<SyncCompetitionsWorker>(12, TimeUnit.HOURS).build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
