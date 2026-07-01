package com.inspekpro.data.local.dao

import androidx.room.*
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.data.local.entity.SessionStatus
import kotlinx.coroutines.flow.Flow

/**
 * Bagian Anom: Data Access Object (DAO) untuk Sesi Inspeksi
 * Fitur: Operasi CRUD Sesi, Filter Status, dan Sinkronisasi.
 * Tujuan: Menangani interaksi database untuk fitur jadwal inspeksi dan pelacakan status sinkronisasi ke Cloud.
 */
@Dao
interface InspectionSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: InspectionSessionEntity): Long

    @Update
    suspend fun updateSession(session: InspectionSessionEntity)

    @Delete
    suspend fun deleteSession(session: InspectionSessionEntity)

    @Query("DELETE FROM inspection_sessions WHERE session_id = :sessionId")
    suspend fun deleteSessionById(sessionId: Long)

    @Query("SELECT * FROM inspection_sessions ORDER BY created_at DESC")
    fun getAllSessions(): Flow<List<InspectionSessionEntity>>

    @Query("SELECT * FROM inspection_sessions WHERE session_id = :sessionId")
    fun getSessionById(sessionId: Long): Flow<InspectionSessionEntity?>

    @Query("SELECT * FROM inspection_sessions WHERE session_id = :sessionId")
    suspend fun getSessionByIdOnce(sessionId: Long): InspectionSessionEntity?

    @Query("SELECT * FROM inspection_sessions WHERE status = :status ORDER BY scheduled_date DESC")
    fun getSessionsByStatus(status: SessionStatus): Flow<List<InspectionSessionEntity>>

    @Query("SELECT * FROM inspection_sessions WHERE inspector_id = :inspectorId ORDER BY created_at DESC")
    fun getSessionsByInspector(inspectorId: String): Flow<List<InspectionSessionEntity>>

    @Query("""
        SELECT * FROM inspection_sessions 
        WHERE scheduled_date BETWEEN :startDate AND :endDate 
        ORDER BY scheduled_date ASC
    """)
    fun getSessionsByDateRange(startDate: Long, endDate: Long): Flow<List<InspectionSessionEntity>>

    @Query("""
        SELECT * FROM inspection_sessions 
        WHERE title LIKE '%' || :query || '%' 
           OR location_name LIKE '%' || :query || '%'
           OR session_code LIKE '%' || :query || '%'
        ORDER BY created_at DESC
    """)
    fun searchSessions(query: String): Flow<List<InspectionSessionEntity>>

    @Query("UPDATE inspection_sessions SET status = :status, updated_at = :updatedAt WHERE session_id = :sessionId")
    suspend fun updateSessionStatus(sessionId: Long, status: SessionStatus, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE inspection_sessions SET start_time = :startTime, status = 'IN_PROGRESS', updated_at = :now WHERE session_id = :sessionId")
    suspend fun startSession(sessionId: Long, startTime: Long, now: Long = System.currentTimeMillis())

    @Query("UPDATE inspection_sessions SET end_time = :endTime, status = 'COMPLETED', updated_at = :now WHERE session_id = :sessionId")
    suspend fun completeSession(sessionId: Long, endTime: Long, now: Long = System.currentTimeMillis())

    @Query("""
        UPDATE inspection_sessions SET 
            weather_condition = :condition,
            weather_temp_celsius = :tempC,
            weather_humidity = :humidity,
            weather_wind_speed = :windSpeed,
            weather_icon = :icon,
            updated_at = :now
        WHERE session_id = :sessionId
    """)
    suspend fun updateWeather(
        sessionId: Long,
        condition: String,
        tempC: Double,
        humidity: Int,
        windSpeed: Double,
        icon: String,
        now: Long = System.currentTimeMillis()
    )

    @Query("SELECT COUNT(*) FROM inspection_sessions")
    fun getTotalSessionCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM inspection_sessions WHERE status = :status")
    fun getSessionCountByStatus(status: SessionStatus): Flow<Int>

    @Query("SELECT * FROM inspection_sessions WHERE is_synced = 0")
    suspend fun getUnsyncedSessions(): List<InspectionSessionEntity>

    @Query("UPDATE inspection_sessions SET is_synced = 1 WHERE session_id = :sessionId")
    suspend fun markAsSynced(sessionId: Long)
}
