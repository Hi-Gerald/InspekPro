package com.inspekpro.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\tH\'J\u001c\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000f2\u0006\u0010\u0011\u001a\u00020\tH\'J\u0016\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0014\u001a\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\fH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u001e\u0010\u0017\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u001a\u00a8\u0006\u001b"}, d2 = {"Lcom/inspekpro/data/local/dao/FindingPhotoDao;", "", "deletePhoto", "", "photo", "Lcom/inspekpro/data/local/entity/FindingPhotoEntity;", "(Lcom/inspekpro/data/local/entity/FindingPhotoEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deletePhotoById", "photoId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPendingUploadPhotos", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPhotoCountByFinding", "Lkotlinx/coroutines/flow/Flow;", "", "findingId", "getPhotosByFinding", "insertPhoto", "insertPhotos", "photos", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markPhotoUploaded", "url", "", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface FindingPhotoDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPhoto(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingPhotoEntity photo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPhotos(@org.jetbrains.annotations.NotNull()
    java.util.List<com.inspekpro.data.local.entity.FindingPhotoEntity> photos, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deletePhoto(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingPhotoEntity photo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM finding_photos WHERE photo_id = :photoId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deletePhotoById(long photoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM finding_photos WHERE finding_id = :findingId ORDER BY taken_at ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.FindingPhotoEntity>> getPhotosByFinding(long findingId);
    
    @androidx.room.Query(value = "SELECT * FROM finding_photos WHERE is_uploaded = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPendingUploadPhotos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.inspekpro.data.local.entity.FindingPhotoEntity>> $completion);
    
    @androidx.room.Query(value = "UPDATE finding_photos SET is_uploaded = 1, remote_url = :url WHERE photo_id = :photoId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markPhotoUploaded(long photoId, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM finding_photos WHERE finding_id = :findingId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getPhotoCountByFinding(long findingId);
}