package com.inspekpro.data.repository

import com.inspekpro.data.local.dao.InspectionFindingDao
import com.inspekpro.data.local.dao.InspectionSessionDao
import com.inspekpro.data.local.dao.SessionSummaryDao
import com.inspekpro.data.local.entity.SessionStatus
import com.inspekpro.data.local.entity.SessionSummaryEntity
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.data.remote.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

/**
 * Bagian Anom: Repository Sesi Inspeksi
 * Fitur: Manajemen data jadwal inspeksi, integrasi cuaca, dan pembuatan ringkasan laporan.
 * Tujuan: Sebagai sumber data utama untuk fitur jadwal inspeksi di seluruh aplikasi.
 *
 * Catatan merge: sumber cuaca disatukan ke BMKG (WeatherRepository) — gratis, tanpa API key,
 * dan konsisten dengan yang dipakai DashboardViewModel. Sebelumnya kelas ini masih memanggil
 * WeatherApiService (OpenWeatherMap) padahal komentar & DashboardViewModel sudah pindah ke BMKG.
 */
class InspectionSessionRepository(
    private val sessionDao: InspectionSessionDao,
    private val findingDao: InspectionFindingDao,
    private val summaryDao: SessionSummaryDao,
    private val weatherRepo: WeatherRepository
) {

    // ─── SESSION CRUD ──────────────────────────────────────────────────────────

    suspend fun createSession(session: InspectionSessionEntity): Long =
        sessionDao.insertSession(session)

    suspend fun updateSession(session: InspectionSessionEntity) {
        sessionDao.updateSession(session)
        sessionDao.markAsUnsynced(session.sessionId)
    }

    fun getAllSessions(): Flow<List<InspectionSessionEntity>> =
        sessionDao.getAllSessions()

    fun getSessionById(id: Long): Flow<InspectionSessionEntity?> =
        sessionDao.getSessionById(id)

    fun getSessionsByStatus(status: SessionStatus): Flow<List<InspectionSessionEntity>> =
        sessionDao.getSessionsByStatus(status)

    fun searchSessions(query: String): Flow<List<InspectionSessionEntity>> =
        sessionDao.searchSessions(query)

    suspend fun startSession(sessionId: Long) {
        sessionDao.startSession(sessionId, System.currentTimeMillis())
        sessionDao.markAsUnsynced(sessionId)
    }

    suspend fun completeSession(sessionId: Long) {
        sessionDao.completeSession(sessionId, System.currentTimeMillis())
        refreshSummary(sessionId)
    }

    suspend fun deleteSession(sessionId: Long) {
        sessionDao.deleteSessionById(sessionId)
    }

    // ─── WEATHER ───────────────────────────────────────────────────────────────

    /**
     * Fetch cuaca dan simpan langsung ke sesi.
     * Dipanggil saat "Buat Sesi Baru" setelah user pilih lokasi.
     */
    suspend fun fetchAndAttachWeather(sessionId: Long, lat: Double, lon: Double): Result<WeatherInfo> {
        val result = weatherRepo.getWeatherByCoords(lat, lon)
        result.onSuccess { info ->
            sessionDao.updateWeather(
                sessionId = sessionId,
                condition = info.conditionDesc,
                tempC    = info.tempCelsius,
                humidity = info.humidity,
                windSpeed = info.windSpeedMs,
                icon     = info.iconCode
            )
        }
        return result
    }

    // ─── SUMMARY ───────────────────────────────────────────────────────────────

    fun getSessionSummary(sessionId: Long): Flow<SessionSummaryEntity?> =
        summaryDao.getSummaryBySession(sessionId)

    /**
     * Hitung ulang ringkasan temuan untuk satu sesi.
     * Dipanggil setiap kali finding berubah.
     */
    suspend fun refreshSummary(sessionId: Long) {
        val raw = findingDao.getFindingSummaryRaw(sessionId)
        val session = sessionDao.getSessionByIdOnce(sessionId) ?: return

        val checked = raw.passCount + raw.failCount
        val score = if (checked > 0) (raw.passCount.toFloat() / checked) * 100f else 0f

        val grade = when {
            score >= 90 -> "A"
            score >= 80 -> "B"
            score >= 70 -> "C"
            score >= 60 -> "D"
            else        -> "E"
        }

        val durationMinutes = if (session.startTime != null && session.endTime != null) {
            ((session.endTime - session.startTime) / 60_000).toInt()
        } else 0

        summaryDao.insertOrUpdateSummary(
            SessionSummaryEntity(
                sessionId         = sessionId,
                totalFindings      = raw.totalFindings,
                criticalCount      = raw.criticalCount,
                majorCount        = raw.majorCount,
                minorCount        = raw.minorCount,
                observationCount  = raw.observationCount,
                passCount          = raw.passCount,
                failCount          = raw.failCount,
                naCount            = raw.naCount,
                complianceScore   = score,
                openFindings      = raw.totalFindings - raw.failCount,
                resolvedFindings  = raw.passCount,
                durationMinutes   = durationMinutes,
                overallGrade      = grade
            )
        )
        
        // Tandai bahwa sesi ini berubah dan butuh disinkronkan lagi
        sessionDao.markAsUnsynced(sessionId)
    }

    // ─── DASHBOARD ─────────────────────────────────────────────────────────────

    suspend fun getDashboardStats() = summaryDao.getDashboardStats()

    fun getTotalSessionCount(): Flow<Int> = sessionDao.getTotalSessionCount()

    fun getCompletedCount(): Flow<Int> =
        sessionDao.getSessionCountByStatus(SessionStatus.COMPLETED)
}
