package com.inspekpro.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0011\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\'J\u001c\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\t0\b2\u0006\u0010\r\u001a\u00020\u000eH\'J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\b2\u0006\u0010\r\u001a\u00020\u000eH\'J$\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\fH\'J\u001c\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\r\u001a\u00020\u000eH\'J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0015J\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u0017\u001a\u00020\fH\'J\u0016\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0019\u001a\u00020\u00032\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010 \u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u001e\u00a8\u0006!"}, d2 = {"Lcom/inspekpro/data/local/dao/ChecklistDao;", "", "deleteItem", "", "item", "Lcom/inspekpro/data/local/entity/ChecklistItemEntity;", "(Lcom/inspekpro/data/local/entity/ChecklistItemEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllActiveTemplates", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/inspekpro/data/local/entity/ChecklistTemplateEntity;", "getCategoriesByTemplate", "", "templateId", "", "getItemCount", "", "getItemsByCategory", "category", "getItemsByTemplate", "getTemplateById", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTemplatesByType", "type", "insertItem", "insertItems", "items", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertTemplate", "template", "(Lcom/inspekpro/data/local/entity/ChecklistTemplateEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateItem", "updateTemplate", "app_debug"})
@androidx.room.Dao()
public abstract interface ChecklistDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertTemplate(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.ChecklistTemplateEntity template, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateTemplate(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.ChecklistTemplateEntity template, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM checklist_templates WHERE is_active = 1 ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.ChecklistTemplateEntity>> getAllActiveTemplates();
    
    @androidx.room.Query(value = "SELECT * FROM checklist_templates WHERE template_id = :templateId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTemplateById(long templateId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.inspekpro.data.local.entity.ChecklistTemplateEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM checklist_templates WHERE inspection_type = :type AND is_active = 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.ChecklistTemplateEntity>> getTemplatesByType(@org.jetbrains.annotations.NotNull()
    java.lang.String type);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertItem(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.ChecklistItemEntity item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertItems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.inspekpro.data.local.entity.ChecklistItemEntity> items, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateItem(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.ChecklistItemEntity item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteItem(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.ChecklistItemEntity item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM checklist_items WHERE template_id = :templateId ORDER BY sort_order ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.ChecklistItemEntity>> getItemsByTemplate(long templateId);
    
    @androidx.room.Query(value = "SELECT * FROM checklist_items WHERE template_id = :templateId AND category = :category ORDER BY sort_order ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.ChecklistItemEntity>> getItemsByCategory(long templateId, @org.jetbrains.annotations.NotNull()
    java.lang.String category);
    
    @androidx.room.Query(value = "SELECT DISTINCT category FROM checklist_items WHERE template_id = :templateId ORDER BY category ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getCategoriesByTemplate(long templateId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM checklist_items WHERE template_id = :templateId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getItemCount(long templateId);
}