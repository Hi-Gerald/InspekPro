package com.inspekpro.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001:\u0001\u0011J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0018\u0010\r\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0010\u00a8\u0006\u0012"}, d2 = {"Lcom/inspekpro/data/local/dao/SessionSummaryDao;", "", "deleteSummaryBySession", "", "sessionId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDashboardStats", "Lcom/inspekpro/data/local/dao/SessionSummaryDao$DashboardStats;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSummaryBySession", "Lkotlinx/coroutines/flow/Flow;", "Lcom/inspekpro/data/local/entity/SessionSummaryEntity;", "getSummaryBySessionOnce", "insertOrUpdateSummary", "summary", "(Lcom/inspekpro/data/local/entity/SessionSummaryEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "DashboardStats", "app_debug"})
@androidx.room.Dao()
public abstract interface SessionSummaryDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertOrUpdateSummary(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.SessionSummaryEntity summary, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM session_summaries WHERE session_id = :sessionId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.inspekpro.data.local.entity.SessionSummaryEntity> getSummaryBySession(long sessionId);
    
    @androidx.room.Query(value = "SELECT * FROM session_summaries WHERE session_id = :sessionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSummaryBySessionOnce(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.inspekpro.data.local.entity.SessionSummaryEntity> $completion);
    
    @androidx.room.Query(value = "DELETE FROM session_summaries WHERE session_id = :sessionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSummaryBySession(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "\n        SELECT \n            COUNT(DISTINCT s.session_id) as totalSessions,\n            SUM(CASE WHEN s.status = \'COMPLETED\' THEN 1 ELSE 0 END) as completedSessions,\n            SUM(COALESCE(sm.total_findings, 0)) as totalFindings,\n            SUM(COALESCE(sm.critical_count, 0)) as totalCritical\n        FROM inspection_sessions s\n        LEFT JOIN session_summaries sm ON s.session_id = sm.session_id\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDashboardStats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.inspekpro.data.local.dao.SessionSummaryDao.DashboardStats> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\u00a8\u0006\u0018"}, d2 = {"Lcom/inspekpro/data/local/dao/SessionSummaryDao$DashboardStats;", "", "totalSessions", "", "completedSessions", "totalFindings", "totalCritical", "(IIII)V", "getCompletedSessions", "()I", "getTotalCritical", "getTotalFindings", "getTotalSessions", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class DashboardStats {
        private final int totalSessions = 0;
        private final int completedSessions = 0;
        private final int totalFindings = 0;
        private final int totalCritical = 0;
        
        public DashboardStats(int totalSessions, int completedSessions, int totalFindings, int totalCritical) {
            super();
        }
        
        public final int getTotalSessions() {
            return 0;
        }
        
        public final int getCompletedSessions() {
            return 0;
        }
        
        public final int getTotalFindings() {
            return 0;
        }
        
        public final int getTotalCritical() {
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
        
        @org.jetbrains.annotations.NotNull()
        public final com.inspekpro.data.local.dao.SessionSummaryDao.DashboardStats copy(int totalSessions, int completedSessions, int totalFindings, int totalCritical) {
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