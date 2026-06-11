package com.inspekpro.data.local.dao

import androidx.room.*
import com.inspekpro.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InspectionFindingDao {

    // ─── INSERT / UPDATE / DELETE ──────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFinding(finding: InspectionFindingEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFindings(findings: List<InspectionFindingEntity>)

    @Update
    suspend fun updateFinding(finding: InspectionFindingEntity)

    @Delete
    suspend fun deleteFinding(finding: InspectionFindingEntity)

    @Query("DELETE FROM inspection_findings WHERE finding_id = :findingId")
    suspend fun deleteFindingById(findingId: Long)

    // ─── QUERIES ──────────────────────────────────────────────────────────────

    @Query("SELECT * FROM inspection_findings WHERE session_id = :sessionId ORDER BY created_at ASC")
    fun getFindingsBySession(sessionId: Long): Flow<List<InspectionFindingEntity>>

    @Query("SELECT * FROM inspection_findings WHERE finding_id = :findingId")
    fun getFindingById(findingId: Long): Flow<InspectionFindingEntity?>

    @Query("SELECT * FROM inspection_findings WHERE finding_id = :findingId")
    suspend fun getFindingByIdOnce(findingId: Long): InspectionFindingEntity?

    @Query("""
        SELECT * FROM inspection_findings 
        WHERE session_id = :sessionId AND severity = :severity
        ORDER BY created_at ASC
    """)
    fun getFindingsBySeverity(sessionId: Long, severity: FindingSeverity): Flow<List<InspectionFindingEntity>>

    @Query("""
        SELECT * FROM inspection_findings 
        WHERE session_id = :sessionId AND status = :status
        ORDER BY created_at ASC
    """)
    fun getFindingsByStatus(sessionId: Long, status: FindingStatus): Flow<List<InspectionFindingEntity>>

    @Query("""
        SELECT * FROM inspection_findings 
        WHERE session_id = :sessionId AND category = :category
        ORDER BY created_at ASC
    """)
    fun getFindingsByCategory(sessionId: Long, category: String): Flow<List<InspectionFindingEntity>>

    // ─── STATUS UPDATE ─────────────────────────────────────────────────────────

    @Query("""
        UPDATE inspection_findings 
        SET result = :result, updated_at = :now 
        WHERE finding_id = :findingId
    """)
    suspend fun updateFindingResult(
        findingId: Long,
        result: FindingResult,
        now: Long = System.currentTimeMillis()
    )

    @Query("""
        UPDATE inspection_findings 
        SET status = :status, updated_at = :now 
        WHERE finding_id = :findingId
    """)
    suspend fun updateFindingStatus(
        findingId: Long,
        status: FindingStatus,
        now: Long = System.currentTimeMillis()
    )

    // ─── AGGREGATES / SUMMARY ─────────────────────────────────────────────────

    @Query("SELECT COUNT(*) FROM inspection_findings WHERE session_id = :sessionId")
    fun getTotalFindingsCount(sessionId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM inspection_findings WHERE session_id = :sessionId AND severity = 'CRITICAL'")
    fun getCriticalCount(sessionId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM inspection_findings WHERE session_id = :sessionId AND severity = 'MAJOR'")
    fun getMajorCount(sessionId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM inspection_findings WHERE session_id = :sessionId AND severity = 'MINOR'")
    fun getMinorCount(sessionId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM inspection_findings WHERE session_id = :sessionId AND result = 'PASS'")
    fun getPassCount(sessionId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM inspection_findings WHERE session_id = :sessionId AND result = 'FAIL'")
    fun getFailCount(sessionId: Long): Flow<Int>

    @Query("SELECT DISTINCT category FROM inspection_findings WHERE session_id = :sessionId")
    fun getCategoriesBySession(sessionId: Long): Flow<List<String>>

    // ─── DATA CLASS FOR SUMMARY QUERY ─────────────────────────────────────────

    data class FindingSummaryRaw(
        val totalFindings: Int,
        val criticalCount: Int,
        val majorCount: Int,
        val minorCount: Int,
        val observationCount: Int,
        val passCount: Int,
        val failCount: Int,
        val naCount: Int
    )

    @Query("""
        SELECT 
            COUNT(*) as totalFindings,
            SUM(CASE WHEN severity = 'CRITICAL' THEN 1 ELSE 0 END) as criticalCount,
            SUM(CASE WHEN severity = 'MAJOR' THEN 1 ELSE 0 END) as majorCount,
            SUM(CASE WHEN severity = 'MINOR' THEN 1 ELSE 0 END) as minorCount,
            SUM(CASE WHEN severity = 'OBSERVATION' THEN 1 ELSE 0 END) as observationCount,
            SUM(CASE WHEN result = 'PASS' THEN 1 ELSE 0 END) as passCount,
            SUM(CASE WHEN result = 'FAIL' THEN 1 ELSE 0 END) as failCount,
            SUM(CASE WHEN result = 'NOT_APPLICABLE' THEN 1 ELSE 0 END) as naCount
        FROM inspection_findings
        WHERE session_id = :sessionId
    """)
    suspend fun getFindingSummaryRaw(sessionId: Long): FindingSummaryRaw
}
