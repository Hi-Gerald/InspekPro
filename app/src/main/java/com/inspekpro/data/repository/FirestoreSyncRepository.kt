package com.inspekpro.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.inspekpro.data.local.dao.InspectionSessionDao
import com.inspekpro.data.local.entity.InspectionSessionEntity
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreSyncRepository @Inject constructor(
    private val sessionDao: InspectionSessionDao,
    private val firestore: FirebaseFirestore
) {

    private val sessionsCollection = firestore.collection("inspection_sessions")

    /**
     * Mengambil semua sesi yang belum disinkronkan dari Room 
     * dan mengunggahnya ke Firestore.
     */
    suspend fun syncUnsyncedSessions(): Result<Int> {
        return try {
            val unsynced = sessionDao.getUnsyncedSessions()
            if (unsynced.isEmpty()) return Result.success(0)

            var count = 0
            for (session in unsynced) {
                // Konversi Entity ke Map untuk Firestore
                val data = sessionToMap(session)
                
                // Gunakan session_code sebagai ID dokumen agar tidak duplikat
                sessionsCollection.document(session.sessionCode).set(data).await()
                
                // Tandai di lokal bahwa sudah di-sync
                sessionDao.markAsSynced(session.sessionId)
                count++
            }
            Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun sessionToMap(session: InspectionSessionEntity): Map<String, Any?> {
        return mapOf(
            "sessionCode" to session.sessionCode,
            "title" to session.title,
            "description" to session.description,
            "locationName" to session.locationName,
            "inspectorName" to session.inspectorName,
            "inspectorId" to session.inspectorId,
            "status" to session.status.name,
            "scheduledDate" to session.scheduledDate,
            "startTime" to session.startTime,
            "endTime" to session.endTime,
            "totalItems" to session.totalItems,
            "passedItems" to session.passedItems,
            "failedItems" to session.failedItems,
            "notes" to session.notes,
            "reportVideoPath" to session.reportVideoPath,
            "createdAt" to session.createdAt,
            "updatedAt" to session.updatedAt
        )
    }
}
