package com.inspekpro.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Bagian Billy: Entity Sesi Inspeksi
 * Fitur: Metadata untuk Video Laporan & Status Sinkronisasi Cloud.
 * Tujuan: Menyimpan informasi lengkap jadwal inspeksi termasuk path video laporan dan flag sinkronisasi Firestore.
 */
@Entity(tableName = "inspection_sessions")
data class InspectionSessionEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "session_id")
    val sessionId: Long = 0,

    @ColumnInfo(name = "session_code")
    val sessionCode: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String = "",

    @ColumnInfo(name = "location_name")
    val locationName: String,

    @ColumnInfo(name = "latitude")
    val latitude: Double? = null,

    @ColumnInfo(name = "longitude")
    val longitude: Double? = null,

    @ColumnInfo(name = "inspector_name")
    val inspectorName: String,

    @ColumnInfo(name = "inspector_id")
    val inspectorId: String,

    @ColumnInfo(name = "status")
    val status: SessionStatus = SessionStatus.DRAFT,

    @ColumnInfo(name = "scheduled_date")
    val scheduledDate: Long,

    @ColumnInfo(name = "start_time")
    val startTime: Long? = null,

    @ColumnInfo(name = "end_time")
    val endTime: Long? = null,

    @ColumnInfo(name = "weather_condition")
    val weatherCondition: String? = null,

    @ColumnInfo(name = "weather_temp_celsius")
    val weatherTempCelsius: Double? = null,

    @ColumnInfo(name = "weather_humidity")
    val weatherHumidity: Int? = null,

    @ColumnInfo(name = "weather_wind_speed")
    val weatherWindSpeed: Double? = null,

    @ColumnInfo(name = "weather_icon")
    val weatherIcon: String? = null,

    @ColumnInfo(name = "total_items")
    val totalItems: Int = 0,

    @ColumnInfo(name = "passed_items")
    val passedItems: Int = 0,

    @ColumnInfo(name = "failed_items")
    val failedItems: Int = 0,

    @ColumnInfo(name = "notes")
    val notes: String = "",

    @ColumnInfo(name = "report_video_path")
    val reportVideoPath: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false
)

enum class SessionStatus {
    DRAFT,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}
