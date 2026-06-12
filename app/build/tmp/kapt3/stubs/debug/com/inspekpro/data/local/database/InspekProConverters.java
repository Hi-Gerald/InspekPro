package com.inspekpro.data.local.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\bH\u0007J\u0014\u0010\t\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\nH\u0007J\u0014\u0010\u000b\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\fH\u0007J\u0014\u0010\r\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0007J\u0014\u0010\u000e\u001a\u0004\u0018\u00010\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0007J\u0014\u0010\u000f\u001a\u0004\u0018\u00010\n2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0007J\u0014\u0010\u0010\u001a\u0004\u0018\u00010\f2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0007\u00a8\u0006\u0011"}, d2 = {"Lcom/inspekpro/data/local/database/InspekProConverters;", "", "()V", "fromFindingResult", "", "value", "Lcom/inspekpro/data/local/entity/FindingResult;", "fromFindingSeverity", "Lcom/inspekpro/data/local/entity/FindingSeverity;", "fromFindingStatus", "Lcom/inspekpro/data/local/entity/FindingStatus;", "fromSessionStatus", "Lcom/inspekpro/data/local/entity/SessionStatus;", "toFindingResult", "toFindingSeverity", "toFindingStatus", "toSessionStatus", "app_debug"})
@kotlin.Suppress(names = {"unused"})
public final class InspekProConverters {
    
    public InspekProConverters() {
        super();
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromSessionStatus(@org.jetbrains.annotations.Nullable()
    com.inspekpro.data.local.entity.SessionStatus value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final com.inspekpro.data.local.entity.SessionStatus toSessionStatus(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromFindingSeverity(@org.jetbrains.annotations.Nullable()
    com.inspekpro.data.local.entity.FindingSeverity value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final com.inspekpro.data.local.entity.FindingSeverity toFindingSeverity(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromFindingStatus(@org.jetbrains.annotations.Nullable()
    com.inspekpro.data.local.entity.FindingStatus value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final com.inspekpro.data.local.entity.FindingStatus toFindingStatus(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromFindingResult(@org.jetbrains.annotations.Nullable()
    com.inspekpro.data.local.entity.FindingResult value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final com.inspekpro.data.local.entity.FindingResult toFindingResult(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
}