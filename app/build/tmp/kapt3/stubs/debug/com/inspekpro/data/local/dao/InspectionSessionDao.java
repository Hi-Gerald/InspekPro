package com.inspekpro.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u0006\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00110\u0010H\'J\u0018\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u00102\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\'J$\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00110\u00102\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u0005H\'J\u001c\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00110\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\'J\u001c\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00110\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\'J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010H\'J\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000b0\u0011H\u00a7@\u00a2\u0006\u0002\u0010!J\u0016\u0010\"\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010#\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00110\u00102\u0006\u0010%\u001a\u00020\u001dH\'J(\u0010&\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\'\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010(\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ(\u0010)\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010*\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010+JH\u0010,\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010-\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00152\u0006\u00101\u001a\u00020/2\u0006\u00102\u001a\u00020\u001d2\b\b\u0002\u0010\u0007\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u00103\u00a8\u00064"}, d2 = {"Lcom/inspekpro/data/local/dao/InspectionSessionDao;", "", "completeSession", "", "sessionId", "", "endTime", "now", "(JJJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSession", "session", "Lcom/inspekpro/data/local/entity/InspectionSessionEntity;", "(Lcom/inspekpro/data/local/entity/InspectionSessionEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSessionById", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllSessions", "Lkotlinx/coroutines/flow/Flow;", "", "getSessionById", "getSessionByIdOnce", "getSessionCountByStatus", "", "status", "Lcom/inspekpro/data/local/entity/SessionStatus;", "getSessionsByDateRange", "startDate", "endDate", "getSessionsByInspector", "inspectorId", "", "getSessionsByStatus", "getTotalSessionCount", "getUnsyncedSessions", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSession", "markAsSynced", "searchSessions", "query", "startSession", "startTime", "updateSession", "updateSessionStatus", "updatedAt", "(JLcom/inspekpro/data/local/entity/SessionStatus;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateWeather", "condition", "tempC", "", "humidity", "windSpeed", "icon", "(JLjava/lang/String;DIDLjava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface InspectionSessionDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSession(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.InspectionSessionEntity session, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSession(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.InspectionSessionEntity session, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSession(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.InspectionSessionEntity session, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM inspection_sessions WHERE session_id = :sessionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSessionById(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM inspection_sessions ORDER BY created_at DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> getAllSessions();
    
    @androidx.room.Query(value = "SELECT * FROM inspection_sessions WHERE session_id = :sessionId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.inspekpro.data.local.entity.InspectionSessionEntity> getSessionById(long sessionId);
    
    @androidx.room.Query(value = "SELECT * FROM inspection_sessions WHERE session_id = :sessionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSessionByIdOnce(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.inspekpro.data.local.entity.InspectionSessionEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM inspection_sessions WHERE status = :status ORDER BY scheduled_date DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> getSessionsByStatus(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.SessionStatus status);
    
    @androidx.room.Query(value = "SELECT * FROM inspection_sessions WHERE inspector_id = :inspectorId ORDER BY created_at DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> getSessionsByInspector(@org.jetbrains.annotations.NotNull()
    java.lang.String inspectorId);
    
    @androidx.room.Query(value = "\n        SELECT * FROM inspection_sessions \n        WHERE scheduled_date BETWEEN :startDate AND :endDate \n        ORDER BY scheduled_date ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> getSessionsByDateRange(long startDate, long endDate);
    
    @androidx.room.Query(value = "\n        SELECT * FROM inspection_sessions \n        WHERE title LIKE \'%\' || :query || \'%\' \n           OR location_name LIKE \'%\' || :query || \'%\'\n           OR session_code LIKE \'%\' || :query || \'%\'\n        ORDER BY created_at DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> searchSessions(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Query(value = "UPDATE inspection_sessions SET status = :status, updated_at = :updatedAt WHERE session_id = :sessionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSessionStatus(long sessionId, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.SessionStatus status, long updatedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE inspection_sessions SET start_time = :startTime, status = \'IN_PROGRESS\', updated_at = :now WHERE session_id = :sessionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object startSession(long sessionId, long startTime, long now, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE inspection_sessions SET end_time = :endTime, status = \'COMPLETED\', updated_at = :now WHERE session_id = :sessionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object completeSession(long sessionId, long endTime, long now, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "\n        UPDATE inspection_sessions SET \n            weather_condition = :condition,\n            weather_temp_celsius = :tempC,\n            weather_humidity = :humidity,\n            weather_wind_speed = :windSpeed,\n            weather_icon = :icon,\n            updated_at = :now\n        WHERE session_id = :sessionId\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateWeather(long sessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String condition, double tempC, int humidity, double windSpeed, @org.jetbrains.annotations.NotNull()
    java.lang.String icon, long now, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM inspection_sessions")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalSessionCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM inspection_sessions WHERE status = :status")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getSessionCountByStatus(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.SessionStatus status);
    
    @androidx.room.Query(value = "SELECT * FROM inspection_sessions WHERE is_synced = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnsyncedSessions(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> $completion);
    
    @androidx.room.Query(value = "UPDATE inspection_sessions SET is_synced = 1 WHERE session_id = :sessionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markAsSynced(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}