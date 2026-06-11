package com.inspekpro.data.local.dao

import androidx.room.*
import com.inspekpro.data.local.entity.ChecklistItemEntity
import com.inspekpro.data.local.entity.ChecklistTemplateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistDao {

    // ─── TEMPLATE ─────────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: ChecklistTemplateEntity): Long

    @Update
    suspend fun updateTemplate(template: ChecklistTemplateEntity)

    @Query("SELECT * FROM checklist_templates WHERE is_active = 1 ORDER BY name ASC")
    fun getAllActiveTemplates(): Flow<List<ChecklistTemplateEntity>>

    @Query("SELECT * FROM checklist_templates WHERE template_id = :templateId")
    suspend fun getTemplateById(templateId: Long): ChecklistTemplateEntity?

    @Query("SELECT * FROM checklist_templates WHERE inspection_type = :type AND is_active = 1")
    fun getTemplatesByType(type: String): Flow<List<ChecklistTemplateEntity>>

    // ─── ITEMS ─────────────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ChecklistItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ChecklistItemEntity>)

    @Update
    suspend fun updateItem(item: ChecklistItemEntity)

    @Delete
    suspend fun deleteItem(item: ChecklistItemEntity)

    @Query("SELECT * FROM checklist_items WHERE template_id = :templateId ORDER BY sort_order ASC")
    fun getItemsByTemplate(templateId: Long): Flow<List<ChecklistItemEntity>>

    @Query("SELECT * FROM checklist_items WHERE template_id = :templateId AND category = :category ORDER BY sort_order ASC")
    fun getItemsByCategory(templateId: Long, category: String): Flow<List<ChecklistItemEntity>>

    @Query("SELECT DISTINCT category FROM checklist_items WHERE template_id = :templateId ORDER BY category ASC")
    fun getCategoriesByTemplate(templateId: Long): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM checklist_items WHERE template_id = :templateId")
    fun getItemCount(templateId: Long): Flow<Int>
}
