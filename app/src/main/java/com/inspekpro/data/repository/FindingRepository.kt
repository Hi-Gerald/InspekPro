package com.inspekpro.data.repository

import com.inspekpro.data.local.dao.FindingPhotoDao
import com.inspekpro.data.local.dao.InspectionFindingDao
import com.inspekpro.data.local.entity.*
import kotlinx.coroutines.flow.Flow

class FindingRepository(
    private val findingDao: InspectionFindingDao,
    private val photoDao: FindingPhotoDao,
    private val sessionRepository: InspectionSessionRepository
) {

    // ─── FINDING CRUD ──────────────────────────────────────────────────────────

    suspend fun addFinding(finding: InspectionFindingEntity): Long {
        val id = findingDao.insertFinding(finding)
        sessionRepository.refreshSummary(finding.sessionId)
        return id
    }

    suspend fun updateFinding(finding: InspectionFindingEntity) {
        findingDao.updateFinding(finding.copy(updatedAt = System.currentTimeMillis()))
        sessionRepository.refreshSummary(finding.sessionId)
    }

    suspend fun deleteFinding(findingId: Long, sessionId: Long) {
        findingDao.deleteFindingById(findingId)
        sessionRepository.refreshSummary(sessionId)
    }

    fun getFindingsBySession(sessionId: Long): Flow<List<InspectionFindingEntity>> =
        findingDao.getFindingsBySession(sessionId)

    fun getFindingById(id: Long): Flow<InspectionFindingEntity?> =
        findingDao.getFindingById(id)

    fun getFindingsBySeverity(sessionId: Long, severity: FindingSeverity): Flow<List<InspectionFindingEntity>> =
        findingDao.getFindingsBySeverity(sessionId, severity)

    // ─── RESULT / STATUS UPDATE ────────────────────────────────────────────────

    suspend fun markFindingResult(findingId: Long, result: FindingResult, sessionId: Long) {
        findingDao.updateFindingResult(findingId, result)
        sessionRepository.refreshSummary(sessionId)
    }

    suspend fun updateFindingStatus(findingId: Long, status: FindingStatus, sessionId: Long) {
        findingDao.updateFindingStatus(findingId, status)
        sessionRepository.refreshSummary(sessionId)
    }

    // ─── PHOTOS ────────────────────────────────────────────────────────────────

    suspend fun addPhoto(photo: FindingPhotoEntity): Long =
        photoDao.insertPhoto(photo)

    suspend fun deletePhoto(photoId: Long) =
        photoDao.deletePhotoById(photoId)

    fun getPhotosByFinding(findingId: Long): Flow<List<FindingPhotoEntity>> =
        photoDao.getPhotosByFinding(findingId)

    fun getPhotoCountByFinding(findingId: Long): Flow<Int> =
        photoDao.getPhotoCountByFinding(findingId)

    // ─── STATS ─────────────────────────────────────────────────────────────────

    fun getTotalFindingsCount(sessionId: Long): Flow<Int> =
        findingDao.getTotalFindingsCount(sessionId)

    fun getCriticalCount(sessionId: Long): Flow<Int> =
        findingDao.getCriticalCount(sessionId)

    fun getCategoriesBySession(sessionId: Long): Flow<List<String>> =
        findingDao.getCategoriesBySession(sessionId)
}
