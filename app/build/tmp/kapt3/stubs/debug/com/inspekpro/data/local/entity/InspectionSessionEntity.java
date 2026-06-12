package com.inspekpro.data.local.entity;

/**
 * Entity: Sesi Inspeksi
 * Menyimpan semua data satu sesi pemeriksaan lapangan
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\bD\b\u0087\b\u0018\u00002\u00020\u0001B\u0085\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001f\u001a\u00020 \u00a2\u0006\u0002\u0010!J\t\u0010E\u001a\u00020\u0003H\u00c6\u0003J\t\u0010F\u001a\u00020\u000fH\u00c6\u0003J\t\u0010G\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010H\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\'J\u0010\u0010I\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\'J\u000b\u0010J\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010K\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010/J\u0010\u0010L\u001a\u0004\u0018\u00010\u0016H\u00c6\u0003\u00a2\u0006\u0002\u0010@J\u0010\u0010M\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010/J\u000b\u0010N\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010O\u001a\u00020\u0016H\u00c6\u0003J\t\u0010P\u001a\u00020\u0005H\u00c6\u0003J\t\u0010Q\u001a\u00020\u0016H\u00c6\u0003J\t\u0010R\u001a\u00020\u0016H\u00c6\u0003J\t\u0010S\u001a\u00020\u0005H\u00c6\u0003J\t\u0010T\u001a\u00020\u0003H\u00c6\u0003J\t\u0010U\u001a\u00020\u0003H\u00c6\u0003J\t\u0010V\u001a\u00020 H\u00c6\u0003J\t\u0010W\u001a\u00020\u0005H\u00c6\u0003J\t\u0010X\u001a\u00020\u0005H\u00c6\u0003J\t\u0010Y\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010Z\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010/J\u0010\u0010[\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010/J\t\u0010\\\u001a\u00020\u0005H\u00c6\u0003J\t\u0010]\u001a\u00020\u0005H\u00c6\u0003J\u009a\u0002\u0010^\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0019\u001a\u00020\u00162\b\b\u0002\u0010\u001a\u001a\u00020\u00162\b\b\u0002\u0010\u001b\u001a\u00020\u00162\b\b\u0002\u0010\u001c\u001a\u00020\u00052\b\b\u0002\u0010\u001d\u001a\u00020\u00032\b\b\u0002\u0010\u001e\u001a\u00020\u00032\b\b\u0002\u0010\u001f\u001a\u00020 H\u00c6\u0001\u00a2\u0006\u0002\u0010_J\u0013\u0010`\u001a\u00020 2\b\u0010a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010b\u001a\u00020\u0016H\u00d6\u0001J\t\u0010c\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\u001d\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0016\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010(\u001a\u0004\b&\u0010\'R\u0016\u0010\u001b\u001a\u00020\u00168\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0016\u0010\r\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010%R\u0016\u0010\f\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010%R\u0016\u0010\u001f\u001a\u00020 8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010-R\u001a\u0010\t\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u00100\u001a\u0004\b.\u0010/R\u0016\u0010\b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010%R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u00100\u001a\u0004\b2\u0010/R\u0016\u0010\u001c\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010%R\u0016\u0010\u001a\u001a\u00020\u00168\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010*R\u0016\u0010\u0010\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010#R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010%R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010#R\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010(\u001a\u0004\b8\u0010\'R\u0016\u0010\u000e\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010%R\u0016\u0010\u0019\u001a\u00020\u00168\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010*R\u0016\u0010\u001e\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010#R\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010%R\u001a\u0010\u0015\u001a\u0004\u0018\u00010\u00168\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010A\u001a\u0004\b?\u0010@R\u0018\u0010\u0018\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010%R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u00100\u001a\u0004\bC\u0010/R\u001a\u0010\u0017\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u00100\u001a\u0004\bD\u0010/\u00a8\u0006d"}, d2 = {"Lcom/inspekpro/data/local/entity/InspectionSessionEntity;", "", "sessionId", "", "sessionCode", "", "title", "description", "locationName", "latitude", "", "longitude", "inspectorName", "inspectorId", "status", "Lcom/inspekpro/data/local/entity/SessionStatus;", "scheduledDate", "startTime", "endTime", "weatherCondition", "weatherTempCelsius", "weatherHumidity", "", "weatherWindSpeed", "weatherIcon", "totalItems", "passedItems", "failedItems", "notes", "createdAt", "updatedAt", "isSynced", "", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Lcom/inspekpro/data/local/entity/SessionStatus;JLjava/lang/Long;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Integer;Ljava/lang/Double;Ljava/lang/String;IIILjava/lang/String;JJZ)V", "getCreatedAt", "()J", "getDescription", "()Ljava/lang/String;", "getEndTime", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getFailedItems", "()I", "getInspectorId", "getInspectorName", "()Z", "getLatitude", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getLocationName", "getLongitude", "getNotes", "getPassedItems", "getScheduledDate", "getSessionCode", "getSessionId", "getStartTime", "getStatus", "()Lcom/inspekpro/data/local/entity/SessionStatus;", "getTitle", "getTotalItems", "getUpdatedAt", "getWeatherCondition", "getWeatherHumidity", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getWeatherIcon", "getWeatherTempCelsius", "getWeatherWindSpeed", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Lcom/inspekpro/data/local/entity/SessionStatus;JLjava/lang/Long;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Integer;Ljava/lang/Double;Ljava/lang/String;IIILjava/lang/String;JJZ)Lcom/inspekpro/data/local/entity/InspectionSessionEntity;", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "inspection_sessions")
public final class InspectionSessionEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    @androidx.room.ColumnInfo(name = "session_id")
    private final long sessionId = 0L;
    @androidx.room.ColumnInfo(name = "session_code")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sessionCode = null;
    @androidx.room.ColumnInfo(name = "title")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String title = null;
    @androidx.room.ColumnInfo(name = "description")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    @androidx.room.ColumnInfo(name = "location_name")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String locationName = null;
    @androidx.room.ColumnInfo(name = "latitude")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double latitude = null;
    @androidx.room.ColumnInfo(name = "longitude")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double longitude = null;
    @androidx.room.ColumnInfo(name = "inspector_name")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String inspectorName = null;
    @androidx.room.ColumnInfo(name = "inspector_id")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String inspectorId = null;
    @androidx.room.ColumnInfo(name = "status")
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.local.entity.SessionStatus status = null;
    @androidx.room.ColumnInfo(name = "scheduled_date")
    private final long scheduledDate = 0L;
    @androidx.room.ColumnInfo(name = "start_time")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long startTime = null;
    @androidx.room.ColumnInfo(name = "end_time")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long endTime = null;
    @androidx.room.ColumnInfo(name = "weather_condition")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String weatherCondition = null;
    @androidx.room.ColumnInfo(name = "weather_temp_celsius")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double weatherTempCelsius = null;
    @androidx.room.ColumnInfo(name = "weather_humidity")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer weatherHumidity = null;
    @androidx.room.ColumnInfo(name = "weather_wind_speed")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double weatherWindSpeed = null;
    @androidx.room.ColumnInfo(name = "weather_icon")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String weatherIcon = null;
    @androidx.room.ColumnInfo(name = "total_items")
    private final int totalItems = 0;
    @androidx.room.ColumnInfo(name = "passed_items")
    private final int passedItems = 0;
    @androidx.room.ColumnInfo(name = "failed_items")
    private final int failedItems = 0;
    @androidx.room.ColumnInfo(name = "notes")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String notes = null;
    @androidx.room.ColumnInfo(name = "created_at")
    private final long createdAt = 0L;
    @androidx.room.ColumnInfo(name = "updated_at")
    private final long updatedAt = 0L;
    @androidx.room.ColumnInfo(name = "is_synced")
    private final boolean isSynced = false;
    
    public InspectionSessionEntity(long sessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionCode, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String locationName, @org.jetbrains.annotations.Nullable()
    java.lang.Double latitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull()
    java.lang.String inspectorName, @org.jetbrains.annotations.NotNull()
    java.lang.String inspectorId, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.SessionStatus status, long scheduledDate, @org.jetbrains.annotations.Nullable()
    java.lang.Long startTime, @org.jetbrains.annotations.Nullable()
    java.lang.Long endTime, @org.jetbrains.annotations.Nullable()
    java.lang.String weatherCondition, @org.jetbrains.annotations.Nullable()
    java.lang.Double weatherTempCelsius, @org.jetbrains.annotations.Nullable()
    java.lang.Integer weatherHumidity, @org.jetbrains.annotations.Nullable()
    java.lang.Double weatherWindSpeed, @org.jetbrains.annotations.Nullable()
    java.lang.String weatherIcon, int totalItems, int passedItems, int failedItems, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, long createdAt, long updatedAt, boolean isSynced) {
        super();
    }
    
    public final long getSessionId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSessionCode() {
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
    public final java.lang.String getLocationName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getLatitude() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getLongitude() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getInspectorName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getInspectorId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.entity.SessionStatus getStatus() {
        return null;
    }
    
    public final long getScheduledDate() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getStartTime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getEndTime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getWeatherCondition() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getWeatherTempCelsius() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getWeatherHumidity() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getWeatherWindSpeed() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getWeatherIcon() {
        return null;
    }
    
    public final int getTotalItems() {
        return 0;
    }
    
    public final int getPassedItems() {
        return 0;
    }
    
    public final int getFailedItems() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNotes() {
        return null;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    public final boolean isSynced() {
        return false;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.entity.SessionStatus component10() {
        return null;
    }
    
    public final long component11() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component16() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component17() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component18() {
        return null;
    }
    
    public final int component19() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component20() {
        return 0;
    }
    
    public final int component21() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component22() {
        return null;
    }
    
    public final long component23() {
        return 0L;
    }
    
    public final long component24() {
        return 0L;
    }
    
    public final boolean component25() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.entity.InspectionSessionEntity copy(long sessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionCode, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String locationName, @org.jetbrains.annotations.Nullable()
    java.lang.Double latitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull()
    java.lang.String inspectorName, @org.jetbrains.annotations.NotNull()
    java.lang.String inspectorId, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.SessionStatus status, long scheduledDate, @org.jetbrains.annotations.Nullable()
    java.lang.Long startTime, @org.jetbrains.annotations.Nullable()
    java.lang.Long endTime, @org.jetbrains.annotations.Nullable()
    java.lang.String weatherCondition, @org.jetbrains.annotations.Nullable()
    java.lang.Double weatherTempCelsius, @org.jetbrains.annotations.Nullable()
    java.lang.Integer weatherHumidity, @org.jetbrains.annotations.Nullable()
    java.lang.Double weatherWindSpeed, @org.jetbrains.annotations.Nullable()
    java.lang.String weatherIcon, int totalItems, int passedItems, int failedItems, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, long createdAt, long updatedAt, boolean isSynced) {
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