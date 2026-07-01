package com.inspekpro.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.inspekpro.data.repository.FirestoreSyncRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class FirestoreSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val firestoreSyncRepository: FirestoreSyncRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("SYNC_WORKER", "Memulai sinkronisasi background...")
        return withContext(Dispatchers.IO) {
            try {
                val result = firestoreSyncRepository.syncUnsyncedSessions()
                if (result.isSuccess) {
                    val count = result.getOrDefault(0)
                    Log.d("SYNC_WORKER", "Sinkronisasi sukses! $count sesi ter-upload.")
                    Result.success()
                } else {
                    Log.e("SYNC_WORKER", "Sinkronisasi gagal", result.exceptionOrNull())
                    Result.retry()
                }
            } catch (e: Exception) {
                Log.e("SYNC_WORKER", "Error tidak terduga saat sinkronisasi", e)
                Result.retry()
            }
        }
    }
}
