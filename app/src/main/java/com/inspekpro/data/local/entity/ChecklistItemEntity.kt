package com.inspekpro.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity: Template Checklist
 * Master data item-item yang harus diperiksa
 */
@Entity(
    tableName = "checklist_items",
    foreignKeys = [
        ForeignKey(
            entity = ChecklistTemplateEntity::class,
            parentColumns = ["template_id"],
            childColumns = ["template_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("template_id")]
)
data class ChecklistItemEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    val itemId: Long = 0,

    @ColumnInfo(name = "template_id")
    val templateId: Long,

    @ColumnInfo(name = "item_code")
    val itemCode: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "sub_category")
    val subCategory: String = "",

    @ColumnInfo(name = "question")
    val question: String,

    @ColumnInfo(name = "description")
    val description: String = "",

    @ColumnInfo(name = "reference_standard")
    val referenceStandard: String = "",         // SNI, ISO, etc.

    @ColumnInfo(name = "is_mandatory")
    val isMandatory: Boolean = true,

    @ColumnInfo(name = "weight")
    val weight: Int = 1,                        // bobot penilaian

    @ColumnInfo(name = "sort_order")
    val sortOrder: Int = 0,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Entity: Template Inspeksi
 * Kumpulan checklist untuk tipe inspeksi tertentu
 */
@Entity(tableName = "checklist_templates")
data class ChecklistTemplateEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "template_id")
    val templateId: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String = "",

    @ColumnInfo(name = "inspection_type")
    val inspectionType: String,                 // Gedung, Peralatan, K3, dll

    @ColumnInfo(name = "version")
    val version: String = "1.0",

    @ColumnInfo(name = "is_active")
    val isActive: Boolean = true,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
