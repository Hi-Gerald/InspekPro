package com.inspekpro.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity: Foto Temuan
 * Menyimpan referensi foto yang diambil per temuan
 */
@Entity(
    tableName = "finding_photos",
    foreignKeys = [
        ForeignKey(
            entity = InspectionFindingEntity::class,
            parentColumns = ["finding_id"],
            childColumns = ["finding_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("finding_id")]
)
data class FindingPhotoEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "photo_id")
    val photoId: Long = 0,

    @ColumnInfo(name = "finding_id")
    val findingId: Long,

    @ColumnInfo(name = "local_path")
    val localPath: String,

    @ColumnInfo(name = "remote_url")
    val remoteUrl: String? = null,

    @ColumnInfo(name = "caption")
    val caption: String = "",

    @ColumnInfo(name = "is_uploaded")
    val isUploaded: Boolean = false,

    @ColumnInfo(name = "file_size_bytes")
    val fileSizeBytes: Long = 0,

    @ColumnInfo(name = "taken_at")
    val takenAt: Long = System.currentTimeMillis()
)

/**
 * Entity: Ringkasan Sesi
 * Cache ringkasan hasil inspeksi per sesi (computed summary)
 */
@Entity(
    tableName = "session_summaries",
    foreignKeys = [
        ForeignKey(
            entity = InspectionSessionEntity::class,
            parentColumns = ["session_id"],
            childColumns = ["session_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["session_id"], unique = true)]
)
data class SessionSummaryEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "summary_id")
    val summaryId: Long = 0,

    @ColumnInfo(name = "session_id")
    val sessionId: Long,

    @ColumnInfo(name = "total_findings")
    val totalFindings: Int = 0,

    @ColumnInfo(name = "critical_count")
    val criticalCount: Int = 0,

    @ColumnInfo(name = "major_count")
    val majorCount: Int = 0,

    @ColumnInfo(name = "minor_count")
    val minorCount: Int = 0,

    @ColumnInfo(name = "observation_count")
    val observationCount: Int = 0,

    @ColumnInfo(name = "pass_count")
    val passCount: Int = 0,

    @ColumnInfo(name = "fail_count")
    val failCount: Int = 0,

    @ColumnInfo(name = "na_count")
    val naCount: Int = 0,

    @ColumnInfo(name = "compliance_score")
    val complianceScore: Float = 0f,            // 0-100%

    @ColumnInfo(name = "open_findings")
    val openFindings: Int = 0,

    @ColumnInfo(name = "resolved_findings")
    val resolvedFindings: Int = 0,

    @ColumnInfo(name = "duration_minutes")
    val durationMinutes: Int = 0,

    @ColumnInfo(name = "overall_grade")
    val overallGrade: String = "",              // A, B, C, D, E

    @ColumnInfo(name = "generated_at")
    val generatedAt: Long = System.currentTimeMillis()
)
