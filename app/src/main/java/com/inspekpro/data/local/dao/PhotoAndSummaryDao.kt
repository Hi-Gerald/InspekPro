package com.inspekpro.data.local.dao

import androidx.room.*
import com.inspekpro.data.local.entity.FindingPhotoEntity
import com.inspekpro.data.local.entity.SessionSummaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FindingPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: FindingPhotoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<FindingPhotoEntity>)

    @Delete
    suspend fun deletePhoto(photo: FindingPhotoEntity)

    @Query("DELETE FROM finding_photos WHERE photo_id = :photoId")
    suspend fun deletePhotoById(photoId: Long)

    @Query("SELECT * FROM finding_photos WHERE finding_id = :findingId ORDER BY taken_at ASC")
    fun getPhotosByFinding(findingId: Long): Flow<List<FindingPhotoEntity>>

    @Query("SELECT * FROM finding_photos WHERE is_uploaded = 0")
    suspend fun getPendingUploadPhotos(): List<FindingPhotoEntity>

    @Query("UPDATE finding_photos SET is_uploaded = 1, remote_url = :url WHERE photo_id = :photoId")
    suspend fun markPhotoUploaded(photoId: Long, url: String)

    @Query("SELECT COUNT(*) FROM finding_photos WHERE finding_id = :findingId")
    fun getPhotoCountByFinding(findingId: Long): Flow<Int>
}

@Dao
interface SessionSummaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateSummary(summary: SessionSummaryEntity): Long

    @Query("SELECT * FROM session_summaries WHERE session_id = :sessionId")
    fun getSummaryBySession(sessionId: Long): Flow<SessionSummaryEntity?>

    @Query("SELECT * FROM session_summaries WHERE session_id = :sessionId")
    suspend fun getSummaryBySessionOnce(sessionId: Long): SessionSummaryEntity?

    @Query("DELETE FROM session_summaries WHERE session_id = :sessionId")
    suspend fun deleteSummaryBySession(sessionId: Long)

    // Dashboard stats: total semua sesi
    data class DashboardStats(
        val totalSessions: Int,
        val completedSessions: Int,
        val totalFindings: Int,
        val totalCritical: Int
    )

    @Query("""
        SELECT 
            COUNT(DISTINCT s.session_id) as totalSessions,
            SUM(CASE WHEN s.status = 'COMPLETED' THEN 1 ELSE 0 END) as completedSessions,
            SUM(COALESCE(sm.total_findings, 0)) as totalFindings,
            SUM(COALESCE(sm.critical_count, 0)) as totalCritical
        FROM inspection_sessions s
        LEFT JOIN session_summaries sm ON s.session_id = sm.session_id
    """)
    suspend fun getDashboardStats(): DashboardStats
}
