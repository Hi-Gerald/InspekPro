package com.inspekpro.data.local.entity;

/**
 * Entity: Temuan Inspeksi (Finding)
 * Setiap item yang diperiksa dalam satu sesi
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b4\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u00a9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0018J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0010H\u00c6\u0003J\t\u00103\u001a\u00020\u0007H\u00c6\u0003J\t\u00104\u001a\u00020\u0007H\u00c6\u0003J\u0010\u00105\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001dJ\t\u00106\u001a\u00020\u0007H\u00c6\u0003J\t\u00107\u001a\u00020\u0007H\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010;\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001dJ\t\u0010<\u001a\u00020\u0007H\u00c6\u0003J\t\u0010=\u001a\u00020\u0007H\u00c6\u0003J\t\u0010>\u001a\u00020\u0007H\u00c6\u0003J\t\u0010?\u001a\u00020\u0007H\u00c6\u0003J\t\u0010@\u001a\u00020\fH\u00c6\u0003J\t\u0010A\u001a\u00020\u000eH\u00c6\u0003J\u00bc\u0001\u0010B\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u00072\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00072\b\b\u0002\u0010\u0015\u001a\u00020\u00072\b\b\u0002\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010CJ\u0013\u0010D\u001a\u00020E2\b\u0010F\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010G\u001a\u00020HH\u00d6\u0001J\t\u0010I\u001a\u00020\u0007H\u00d6\u0001R\u0016\u0010\u0014\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u0016\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0016\u0010\n\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001aR\u001a\u0010\u0013\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b\"\u0010\u001dR\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001aR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010 R\u0016\u0010\u0011\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001aR\u0016\u0010\u0015\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001aR\u0016\u0010\u0012\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001aR\u0016\u0010\u000f\u001a\u00020\u00108\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010 R\u0016\u0010\u000b\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0016\u0010\r\u001a\u00020\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0016\u0010\t\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001aR\u0016\u0010\u0017\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010 \u00a8\u0006J"}, d2 = {"Lcom/inspekpro/data/local/entity/InspectionFindingEntity;", "", "findingId", "", "sessionId", "checklistItemId", "findingCode", "", "category", "title", "description", "severity", "Lcom/inspekpro/data/local/entity/FindingSeverity;", "status", "Lcom/inspekpro/data/local/entity/FindingStatus;", "result", "Lcom/inspekpro/data/local/entity/FindingResult;", "locationDetail", "recommendation", "dueDate", "assignedTo", "photoPaths", "createdAt", "updatedAt", "(JJLjava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/inspekpro/data/local/entity/FindingSeverity;Lcom/inspekpro/data/local/entity/FindingStatus;Lcom/inspekpro/data/local/entity/FindingResult;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;JJ)V", "getAssignedTo", "()Ljava/lang/String;", "getCategory", "getChecklistItemId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getCreatedAt", "()J", "getDescription", "getDueDate", "getFindingCode", "getFindingId", "getLocationDetail", "getPhotoPaths", "getRecommendation", "getResult", "()Lcom/inspekpro/data/local/entity/FindingResult;", "getSessionId", "getSeverity", "()Lcom/inspekpro/data/local/entity/FindingSeverity;", "getStatus", "()Lcom/inspekpro/data/local/entity/FindingStatus;", "getTitle", "getUpdatedAt", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JJLjava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/inspekpro/data/local/entity/FindingSeverity;Lcom/inspekpro/data/local/entity/FindingStatus;Lcom/inspekpro/data/local/entity/FindingResult;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;JJ)Lcom/inspekpro/data/local/entity/InspectionFindingEntity;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
@androidx.room.Entity(tableName = "inspection_findings", foreignKeys = {@androidx.room.ForeignKey(entity = com.inspekpro.data.local.entity.InspectionSessionEntity.class, parentColumns = {"session_id"}, childColumns = {"session_id"}, onDelete = 5), @androidx.room.ForeignKey(entity = com.inspekpro.data.local.entity.ChecklistItemEntity.class, parentColumns = {"item_id"}, childColumns = {"checklist_item_id"}, onDelete = 3)}, indices = {@androidx.room.Index(value = {"session_id"}), @androidx.room.Index(value = {"checklist_item_id"})})
public final class InspectionFindingEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    @androidx.room.ColumnInfo(name = "finding_id")
    private final long findingId = 0L;
    @androidx.room.ColumnInfo(name = "session_id")
    private final long sessionId = 0L;
    @androidx.room.ColumnInfo(name = "checklist_item_id")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long checklistItemId = null;
    @androidx.room.ColumnInfo(name = "finding_code")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String findingCode = null;
    @androidx.room.ColumnInfo(name = "category")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String category = null;
    @androidx.room.ColumnInfo(name = "title")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String title = null;
    @androidx.room.ColumnInfo(name = "description")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    @androidx.room.ColumnInfo(name = "severity")
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.local.entity.FindingSeverity severity = null;
    @androidx.room.ColumnInfo(name = "status")
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.local.entity.FindingStatus status = null;
    @androidx.room.ColumnInfo(name = "result")
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.local.entity.FindingResult result = null;
    @androidx.room.ColumnInfo(name = "location_detail")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String locationDetail = null;
    @androidx.room.ColumnInfo(name = "recommendation")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String recommendation = null;
    @androidx.room.ColumnInfo(name = "due_date")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long dueDate = null;
    @androidx.room.ColumnInfo(name = "assigned_to")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String assignedTo = null;
    @androidx.room.ColumnInfo(name = "photo_paths")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String photoPaths = null;
    @androidx.room.ColumnInfo(name = "created_at")
    private final long createdAt = 0L;
    @androidx.room.ColumnInfo(name = "updated_at")
    private final long updatedAt = 0L;
    
    public InspectionFindingEntity(long findingId, long sessionId, @org.jetbrains.annotations.Nullable()
    java.lang.Long checklistItemId, @org.jetbrains.annotations.NotNull()
    java.lang.String findingCode, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingSeverity severity, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingStatus status, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingResult result, @org.jetbrains.annotations.NotNull()
    java.lang.String locationDetail, @org.jetbrains.annotations.NotNull()
    java.lang.String recommendation, @org.jetbrains.annotations.Nullable()
    java.lang.Long dueDate, @org.jetbrains.annotations.NotNull()
    java.lang.String assignedTo, @org.jetbrains.annotations.NotNull()
    java.lang.String photoPaths, long createdAt, long updatedAt) {
        super();
    }
    
    public final long getFindingId() {
        return 0L;
    }
    
    public final long getSessionId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getChecklistItemId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFindingCode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.entity.FindingSeverity getSeverity() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.entity.FindingStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.entity.FindingResult getResult() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLocationDetail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRecommendation() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getDueDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAssignedTo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPhotoPaths() {
        return null;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.entity.FindingResult component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    public final long component16() {
        return 0L;
    }
    
    public final long component17() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.entity.FindingSeverity component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.entity.FindingStatus component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.entity.InspectionFindingEntity copy(long findingId, long sessionId, @org.jetbrains.annotations.Nullable()
    java.lang.Long checklistItemId, @org.jetbrains.annotations.NotNull()
    java.lang.String findingCode, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingSeverity severity, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingStatus status, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingResult result, @org.jetbrains.annotations.NotNull()
    java.lang.String locationDetail, @org.jetbrains.annotations.NotNull()
    java.lang.String recommendation, @org.jetbrains.annotations.Nullable()
    java.lang.Long dueDate, @org.jetbrains.annotations.NotNull()
    java.lang.String assignedTo, @org.jetbrains.annotations.NotNull()
    java.lang.String photoPaths, long createdAt, long updatedAt) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}