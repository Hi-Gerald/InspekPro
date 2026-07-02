package com.inspekpro.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.inspekpro.data.local.dao.FindingPhotoDao
import com.inspekpro.data.local.dao.InspectionFindingDao
import com.inspekpro.data.local.dao.InspectionSessionDao
import com.inspekpro.data.local.entity.InspectionFindingEntity
import com.inspekpro.data.local.entity.InspectionSessionEntity
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Bagian Billy: Firestore Sync Repository
 * Fitur: Sinkronisasi Cloud (Firebase Firestore)
 * Untuk: Mengunggah data sesi inspeksi dari database lokal (Room) ke cloud secara otomatis agar data aman dan sinkron antar perangkat.
 */
@Singleton
class FirestoreSyncRepository @Inject constructor(
    private val sessionDao: InspectionSessionDao,
    private val findingDao: InspectionFindingDao,
    private val photoDao: FindingPhotoDao,
    private val firestore: FirebaseFirestore?
) {

    private val sessionsCollection = firestore?.collection("inspection_sessions")

    suspend fun syncUnsyncedSessions(): Result<Int> {
        val db = firestore ?: return Result.success(0)
        val collection = sessionsCollection ?: return Result.success(0)
        
        return try {
            val unsynced = sessionDao.getUnsyncedSessions()
            if (unsynced.isEmpty()) return Result.success(0)

            var count = 0
            for (session in unsynced) {
                // Prepare session data
                val sessionData = sessionToMap(session)
                val sessionDocRef = collection.document(session.sessionCode)

                // Start a batch for atomicity
                val batch = db.batch()
                batch.set(sessionDocRef, sessionData, SetOptions.merge())

                // Fetch findings
                val findings = findingDao.getFindingsBySessionOnce(session.sessionId)
                
                val findingsCollection = sessionDocRef.collection("findings")
                
                for (finding in findings) {
                    val findingDocRef = findingsCollection.document(finding.findingId.toString())
                    
                    // Fetch photos for finding
                    val photos = photoDao.getPhotosByFindingOnce(finding.findingId)
                    val photoUrls = photos.mapNotNull { it.remoteUrl }
                    
                    val findingData = findingToMap(finding, photoUrls)
                    batch.set(findingDocRef, findingData, SetOptions.merge())
                }

                // Commit the batch
                batch.commit().await()
                
                // Mark session as synced locally
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

    private fun findingToMap(finding: InspectionFindingEntity, photoUrls: List<String>): Map<String, Any?> {
        return mapOf(
            "findingId" to finding.findingId,
            "checklistItemId" to finding.checklistItemId,
            "findingCode" to finding.findingCode,
            "category" to finding.category,
            "title" to finding.title,
            "description" to finding.description,
            "severity" to finding.severity.name,
            "status" to finding.status.name,
            "result" to finding.result.name,
            "locationDetail" to finding.locationDetail,
            "recommendation" to finding.recommendation,
            "dueDate" to finding.dueDate,
            "assignedTo" to finding.assignedTo,
            "photoUrls" to photoUrls, // Array of Firebase Storage URLs
            "createdAt" to finding.createdAt,
            "updatedAt" to finding.updatedAt
        )
    }
}
