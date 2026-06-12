package com.inspekpro.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001:\u00012J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010\u000f\u001a\u00020\tH\'J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f2\u0006\u0010\u000f\u001a\u00020\tH\'J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\f2\u0006\u0010\u000f\u001a\u00020\tH\'J\u0018\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\f2\u0006\u0010\b\u001a\u00020\tH\'J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ$\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u000eH\'J\u001c\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u000f\u001a\u00020\tH\'J$\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u001cH\'J$\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u001fH\'J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00110\f2\u0006\u0010\u000f\u001a\u00020\tH\'J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00110\f2\u0006\u0010\u000f\u001a\u00020\tH\'J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00110\f2\u0006\u0010\u000f\u001a\u00020\tH\'J\u001c\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010$\u001a\u00020\u0011H\'J\u0016\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00110\f2\u0006\u0010\u000f\u001a\u00020\tH\'J\u0016\u0010&\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\'\u001a\u00020\u00032\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00050\rH\u00a7@\u00a2\u0006\u0002\u0010)J\u0016\u0010*\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J(\u0010+\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010,\u001a\u00020-2\b\b\u0002\u0010.\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010/J(\u00100\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010.\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u00101\u00a8\u00063"}, d2 = {"Lcom/inspekpro/data/local/dao/InspectionFindingDao;", "", "deleteFinding", "", "finding", "Lcom/inspekpro/data/local/entity/InspectionFindingEntity;", "(Lcom/inspekpro/data/local/entity/InspectionFindingEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteFindingById", "findingId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCategoriesBySession", "Lkotlinx/coroutines/flow/Flow;", "", "", "sessionId", "getCriticalCount", "", "getFailCount", "getFindingById", "getFindingByIdOnce", "getFindingSummaryRaw", "Lcom/inspekpro/data/local/dao/InspectionFindingDao$FindingSummaryRaw;", "getFindingsByCategory", "category", "getFindingsBySession", "getFindingsBySeverity", "severity", "Lcom/inspekpro/data/local/entity/FindingSeverity;", "getFindingsByStatus", "status", "Lcom/inspekpro/data/local/entity/FindingStatus;", "getMajorCount", "getMinorCount", "getPassCount", "getRecentFindings", "limit", "getTotalFindingsCount", "insertFinding", "insertFindings", "findings", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateFinding", "updateFindingResult", "result", "Lcom/inspekpro/data/local/entity/FindingResult;", "now", "(JLcom/inspekpro/data/local/entity/FindingResult;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateFindingStatus", "(JLcom/inspekpro/data/local/entity/FindingStatus;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "FindingSummaryRaw", "app_debug"})
@androidx.room.Dao()
public abstract interface InspectionFindingDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertFinding(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.InspectionFindingEntity finding, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertFindings(@org.jetbrains.annotations.NotNull()
    java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity> findings, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateFinding(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.InspectionFindingEntity finding, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteFinding(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.InspectionFindingEntity finding, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM inspection_findings WHERE finding_id = :findingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteFindingById(long findingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM inspection_findings WHERE session_id = :sessionId ORDER BY created_at ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> getFindingsBySession(long sessionId);
    
    @androidx.room.Query(value = "SELECT * FROM inspection_findings WHERE finding_id = :findingId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.inspekpro.data.local.entity.InspectionFindingEntity> getFindingById(long findingId);
    
    @androidx.room.Query(value = "SELECT * FROM inspection_findings WHERE finding_id = :findingId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFindingByIdOnce(long findingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.inspekpro.data.local.entity.InspectionFindingEntity> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM inspection_findings \n        WHERE session_id = :sessionId AND severity = :severity\n        ORDER BY created_at ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> getFindingsBySeverity(long sessionId, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingSeverity severity);
    
    @androidx.room.Query(value = "\n        SELECT * FROM inspection_findings \n        WHERE session_id = :sessionId AND status = :status\n        ORDER BY created_at ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> getFindingsByStatus(long sessionId, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingStatus status);
    
    @androidx.room.Query(value = "\n        SELECT * FROM inspection_findings \n        WHERE session_id = :sessionId AND category = :category\n        ORDER BY created_at ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> getFindingsByCategory(long sessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String category);
    
    @androidx.room.Query(value = "SELECT * FROM inspection_findings ORDER BY created_at DESC LIMIT :limit")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> getRecentFindings(int limit);
    
    @androidx.room.Query(value = "\n        UPDATE inspection_findings \n        SET result = :result, updated_at = :now \n        WHERE finding_id = :findingId\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateFindingResult(long findingId, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingResult result, long now, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "\n        UPDATE inspection_findings \n        SET status = :status, updated_at = :now \n        WHERE finding_id = :findingId\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateFindingStatus(long findingId, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingStatus status, long now, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM inspection_findings WHERE session_id = :sessionId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalFindingsCount(long sessionId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM inspection_findings WHERE session_id = :sessionId AND severity = \'CRITICAL\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getCriticalCount(long sessionId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM inspection_findings WHERE session_id = :sessionId AND severity = \'MAJOR\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getMajorCount(long sessionId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM inspection_findings WHERE session_id = :sessionId AND severity = \'MINOR\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getMinorCount(long sessionId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM inspection_findings WHERE session_id = :sessionId AND result = \'PASS\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getPassCount(long sessionId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM inspection_findings WHERE session_id = :sessionId AND result = \'FAIL\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getFailCount(long sessionId);
    
    @androidx.room.Query(value = "SELECT DISTINCT category FROM inspection_findings WHERE session_id = :sessionId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getCategoriesBySession(long sessionId);
    
    @androidx.room.Query(value = "\n        SELECT \n            COUNT(*) as totalFindings,\n            SUM(CASE WHEN severity = \'CRITICAL\' THEN 1 ELSE 0 END) as criticalCount,\n            SUM(CASE WHEN severity = \'MAJOR\' THEN 1 ELSE 0 END) as majorCount,\n            SUM(CASE WHEN severity = \'MINOR\' THEN 1 ELSE 0 END) as minorCount,\n            SUM(CASE WHEN severity = \'OBSERVATION\' THEN 1 ELSE 0 END) as observationCount,\n            SUM(CASE WHEN result = \'PASS\' THEN 1 ELSE 0 END) as passCount,\n            SUM(CASE WHEN result = \'FAIL\' THEN 1 ELSE 0 END) as failCount,\n            SUM(CASE WHEN result = \'NOT_APPLICABLE\' THEN 1 ELSE 0 END) as naCount\n        FROM inspection_findings\n        WHERE session_id = :sessionId\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFindingSummaryRaw(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.inspekpro.data.local.dao.InspectionFindingDao.FindingSummaryRaw> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003JY\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\"\u001a\u00020#H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\r\u00a8\u0006$"}, d2 = {"Lcom/inspekpro/data/local/dao/InspectionFindingDao$FindingSummaryRaw;", "", "totalFindings", "", "criticalCount", "majorCount", "minorCount", "observationCount", "passCount", "failCount", "naCount", "(IIIIIIII)V", "getCriticalCount", "()I", "getFailCount", "getMajorCount", "getMinorCount", "getNaCount", "getObservationCount", "getPassCount", "getTotalFindings", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class FindingSummaryRaw {
        private final int totalFindings = 0;
        private final int criticalCount = 0;
        private final int majorCount = 0;
        private final int minorCount = 0;
        private final int observationCount = 0;
        private final int passCount = 0;
        private final int failCount = 0;
        private final int naCount = 0;
        
        public FindingSummaryRaw(int totalFindings, int criticalCount, int majorCount, int minorCount, int observationCount, int passCount, int failCount, int naCount) {
            super();
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
        
        public final int component1() {
            return 0;
        }
        
        public final int component2() {
            return 0;
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
        
        @org.jetbrains.annotations.NotNull()
        public final com.inspekpro.data.local.dao.InspectionFindingDao.FindingSummaryRaw copy(int totalFindings, int criticalCount, int majorCount, int minorCount, int observationCount, int passCount, int failCount, int naCount) {
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
}