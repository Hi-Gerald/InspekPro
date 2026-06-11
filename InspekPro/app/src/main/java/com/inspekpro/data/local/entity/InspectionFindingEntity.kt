package com.inspekpro.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity: Temuan Inspeksi (Finding)
 * Setiap item yang diperiksa dalam satu sesi
 */
@Entity(
    tableName = "inspection_findings",
    foreignKeys = [
        ForeignKey(
            entity = InspectionSessionEntity::class,
            parentColumns = ["session_id"],
            childColumns = ["session_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ChecklistItemEntity::class,
            parentColumns = ["item_id"],
            childColumns = ["checklist_item_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("session_id"),
        Index("checklist_item_id")
    ]
)
data class InspectionFindingEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "finding_id")
    val findingId: Long = 0,

    @ColumnInfo(name = "session_id")
    val sessionId: Long,

    @ColumnInfo(name = "checklist_item_id")
    val checklistItemId: Long? = null,

    @ColumnInfo(name = "finding_code")
    val findingCode: String,                    // e.g. "F-001"

    @ColumnInfo(name = "category")
    val category: String,                       // Struktural, Mekanikal, Elektrikal, dll

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "severity")
    val severity: FindingSeverity = FindingSeverity.MINOR,

    @ColumnInfo(name = "status")
    val status: FindingStatus = FindingStatus.OPEN,

    @ColumnInfo(name = "result")
    val result: FindingResult = FindingResult.NOT_CHECKED,

    @ColumnInfo(name = "location_detail")
    val locationDetail: String = "",            // "Lantai 2, Ruang Server"

    @ColumnInfo(name = "recommendation")
    val recommendation: String = "",

    @ColumnInfo(name = "due_date")
    val dueDate: Long? = null,

    @ColumnInfo(name = "assigned_to")
    val assignedTo: String = "",

    @ColumnInfo(name = "photo_paths")
    val photoPaths: String = "",               // JSON array of local paths

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

enum class FindingSeverity {
    CRITICAL,   // Kritis - hentikan operasi
    MAJOR,      // Mayor - perbaiki segera
    MINOR,      // Minor - perbaiki terjadwal
    OBSERVATION // Observasi - pantau saja
}

enum class FindingStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED,
    DEFERRED
}

enum class FindingResult {
    NOT_CHECKED,
    PASS,
    FAIL,
    NOT_APPLICABLE
}
