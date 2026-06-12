package com.inspekpro.data.local.entity;

/**
 * Entity: Ringkasan Sesi
 * Cache ringkasan hasil inspeksi per sesi (computed summary)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b(\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u00a3\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u0012\b\b\u0002\u0010\n\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\f\u001a\u00020\u0006\u0012\b\b\u0002\u0010\r\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0016J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0006H\u00c6\u0003J\t\u0010-\u001a\u00020\u000fH\u00c6\u0003J\t\u0010.\u001a\u00020\u0006H\u00c6\u0003J\t\u0010/\u001a\u00020\u0006H\u00c6\u0003J\t\u00100\u001a\u00020\u0006H\u00c6\u0003J\t\u00101\u001a\u00020\u0014H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0006H\u00c6\u0003J\t\u00105\u001a\u00020\u0006H\u00c6\u0003J\t\u00106\u001a\u00020\u0006H\u00c6\u0003J\t\u00107\u001a\u00020\u0006H\u00c6\u0003J\t\u00108\u001a\u00020\u0006H\u00c6\u0003J\t\u00109\u001a\u00020\u0006H\u00c6\u0003J\t\u0010:\u001a\u00020\u0006H\u00c6\u0003J\u00a9\u0001\u0010;\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\r\u001a\u00020\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00062\b\b\u0002\u0010\u0011\u001a\u00020\u00062\b\b\u0002\u0010\u0012\u001a\u00020\u00062\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010<\u001a\u00020=2\b\u0010>\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010?\u001a\u00020\u0006H\u00d6\u0001J\t\u0010@\u001a\u00020\u0014H\u00d6\u0001R\u0016\u0010\u000e\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\u0012\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0016\u0010\f\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001aR\u0016\u0010\u0015\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010\b\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001aR\u0016\u0010\t\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001aR\u0016\u0010\r\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001aR\u0016\u0010\n\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001aR\u0016\u0010\u0010\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001aR\u0016\u0010\u0013\u001a\u00020\u00148\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0016\u0010\u000b\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001aR\u0016\u0010\u0011\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001aR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001eR\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001a\u00a8\u0006A"}, d2 = {"Lcom/inspekpro/data/local/entity/SessionSummaryEntity;", "", "summaryId", "", "sessionId", "totalFindings", "", "criticalCount", "majorCount", "minorCount", "observationCount", "passCount", "failCount", "naCount", "complianceScore", "", "openFindings", "resolvedFindings", "durationMinutes", "overallGrade", "", "generatedAt", "(JJIIIIIIIIFIIILjava/lang/String;J)V", "getComplianceScore", "()F", "getCriticalCount", "()I", "getDurationMinutes", "getFailCount", "getGeneratedAt", "()J", "getMajorCount", "getMinorCount", "getNaCount", "getObservationCount", "getOpenFindings", "getOverallGrade", "()Ljava/lang/String;", "getPassCount", "getResolvedFindings", "getSessionId", "getSummaryId", "getTotalFindings", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "session_summaries", foreignKeys = {@androidx.room.ForeignKey(entity = com.inspekpro.data.local.entity.InspectionSessionEntity.class, parentColumns = {"session_id"}, childColumns = {"session_id"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"session_id"}, unique = true)})
public final class SessionSummaryEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    @androidx.room.ColumnInfo(name = "summary_id")
    private final long summaryId = 0L;
    @androidx.room.ColumnInfo(name = "session_id")
    private final long sessionId = 0L;
    @androidx.room.ColumnInfo(name = "total_findings")
    private final int totalFindings = 0;
    @androidx.room.ColumnInfo(name = "critical_count")
    private final int criticalCount = 0;
    @androidx.room.ColumnInfo(name = "major_count")
    private final int majorCount = 0;
    @androidx.room.ColumnInfo(name = "minor_count")
    private final int minorCount = 0;
    @androidx.room.ColumnInfo(name = "observation_count")
    private final int observationCount = 0;
    @androidx.room.ColumnInfo(name = "pass_count")
    private final int passCount = 0;
    @androidx.room.ColumnInfo(name = "fail_count")
    private final int failCount = 0;
    @androidx.room.ColumnInfo(name = "na_count")
    private final int naCount = 0;
    @androidx.room.ColumnInfo(name = "compliance_score")
    private final float complianceScore = 0.0F;
    @androidx.room.ColumnInfo(name = "open_findings")
    private final int openFindings = 0;
    @androidx.room.ColumnInfo(name = "resolved_findings")
    private final int resolvedFindings = 0;
    @androidx.room.ColumnInfo(name = "duration_minutes")
    private final int durationMinutes = 0;
    @androidx.room.ColumnInfo(name = "overall_grade")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String overallGrade = null;
    @androidx.room.ColumnInfo(name = "generated_at")
    private final long generatedAt = 0L;
    
    public SessionSummaryEntity(long summaryId, long sessionId, int totalFindings, int criticalCount, int majorCount, int minorCount, int observationCount, int passCount, int failCount, int naCount, float complianceScore, int openFindings, int resolvedFindings, int durationMinutes, @org.jetbrains.annotations.NotNull()
    java.lang.String overallGrade, long generatedAt) {
        super();
    }
    
    public final long getSummaryId() {
        return 0L;
    }
    
    public final long getSessionId() {
        return 0L;
    }
    
    public final int getTotalFindings() {
        return 0;
    }
    
    public final int getCriticalCount() {
        return 0;
    }
    
    public final int getMajorCount() {
        return 0;
    }
    
    public final int getMinorCount() {
        return 0;
    }
    
    public final int getObservationCount() {
        return 0;
    }
    
    public final int getPassCount() {
        return 0;
    }
    
    public final int getFailCount() {
        return 0;
    }
    
    public final int getNaCount() {
        return 0;
    }
    
    public final float getComplianceScore() {
        return 0.0F;
    }
    
    public final int getOpenFindings() {
        return 0;
    }
    
    public final int getResolvedFindings() {
        return 0;
    }
    
    public final int getDurationMinutes() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOverallGrade() {
        return null;
    }
    
    public final long getGeneratedAt() {
        return 0L;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final float component11() {
        return 0.0F;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final int component13() {
        return 0;
    }
    
    public final int component14() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    public final long component16() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.entity.SessionSummaryEntity copy(long summaryId, long sessionId, int totalFindings, int criticalCount, int majorCount, int minorCount, int observationCount, int passCount, int failCount, int naCount, float complianceScore, int openFindings, int resolvedFindings, int durationMinutes, @org.jetbrains.annotations.NotNull()
    java.lang.String overallGrade, long generatedAt) {
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