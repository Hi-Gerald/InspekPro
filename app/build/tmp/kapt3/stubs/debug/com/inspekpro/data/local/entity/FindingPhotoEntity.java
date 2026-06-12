package com.inspekpro.data.local.entity;

/**
 * Entity: Foto Temuan
 * Menyimpan referensi foto yang diambil per temuan
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0019\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BS\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J[\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010!\u001a\u00020\n2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020$H\u00d6\u0001J\t\u0010%\u001a\u00020\u0006H\u00d6\u0001R\u0016\u0010\b\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0013R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0016\u0010\f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011\u00a8\u0006&"}, d2 = {"Lcom/inspekpro/data/local/entity/FindingPhotoEntity;", "", "photoId", "", "findingId", "localPath", "", "remoteUrl", "caption", "isUploaded", "", "fileSizeBytes", "takenAt", "(JJLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZJJ)V", "getCaption", "()Ljava/lang/String;", "getFileSizeBytes", "()J", "getFindingId", "()Z", "getLocalPath", "getPhotoId", "getRemoteUrl", "getTakenAt", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
@androidx.room.Entity(tableName = "finding_photos", foreignKeys = {@androidx.room.ForeignKey(entity = com.inspekpro.data.local.entity.InspectionFindingEntity.class, parentColumns = {"finding_id"}, childColumns = {"finding_id"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"finding_id"})})
public final class FindingPhotoEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    @androidx.room.ColumnInfo(name = "photo_id")
    private final long photoId = 0L;
    @androidx.room.ColumnInfo(name = "finding_id")
    private final long findingId = 0L;
    @androidx.room.ColumnInfo(name = "local_path")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String localPath = null;
    @androidx.room.ColumnInfo(name = "remote_url")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String remoteUrl = null;
    @androidx.room.ColumnInfo(name = "caption")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String caption = null;
    @androidx.room.ColumnInfo(name = "is_uploaded")
    private final boolean isUploaded = false;
    @androidx.room.ColumnInfo(name = "file_size_bytes")
    private final long fileSizeBytes = 0L;
    @androidx.room.ColumnInfo(name = "taken_at")
    private final long takenAt = 0L;
    
    public FindingPhotoEntity(long photoId, long findingId, @org.jetbrains.annotations.NotNull()
    java.lang.String localPath, @org.jetbrains.annotations.Nullable()
    java.lang.String remoteUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String caption, boolean isUploaded, long fileSizeBytes, long takenAt) {
        super();
    }
    
    public final long getPhotoId() {
        return 0L;
    }
    
    public final long getFindingId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLocalPath() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRemoteUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCaption() {
        return null;
    }
    
    public final boolean isUploaded() {
        return false;
    }
    
    public final long getFileSizeBytes() {
        return 0L;
    }
    
    public final long getTakenAt() {
        return 0L;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final long component7() {
        return 0L;
    }
    
    public final long component8() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.entity.FindingPhotoEntity copy(long photoId, long findingId, @org.jetbrains.annotations.NotNull()
    java.lang.String localPath, @org.jetbrains.annotations.Nullable()
    java.lang.String remoteUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String caption, boolean isUploaded, long fileSizeBytes, long takenAt) {
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