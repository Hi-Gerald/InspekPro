package com.inspekpro.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0019J\u001a\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\u001c0\u001b2\u0006\u0010\u0015\u001a\u00020\nJ\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001b2\u0006\u0010\u0015\u001a\u00020\nJ\u0016\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u001b2\u0006\u0010!\u001a\u00020\nJ\u001a\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u001c0\u001b2\u0006\u0010\u0015\u001a\u00020\nJ\"\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u001c0\u001b2\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010$\u001a\u00020%J\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001b2\u0006\u0010\u0014\u001a\u00020\nJ\u001a\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u001c0\u001b2\u0006\u0010\u0014\u001a\u00020\nJ\u001a\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u001c0\u001b2\u0006\u0010)\u001a\u00020\u001fJ\u0014\u0010*\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001b2\u0006\u0010\u0015\u001a\u00020\nJ&\u0010+\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010,\u001a\u00020-2\u0006\u0010\u0015\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010.J\u0016\u0010/\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ&\u00100\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\n2\u0006\u00101\u001a\u0002022\u0006\u0010\u0015\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u00103R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/inspekpro/data/repository/FindingRepository;", "", "findingDao", "Lcom/inspekpro/data/local/dao/InspectionFindingDao;", "photoDao", "Lcom/inspekpro/data/local/dao/FindingPhotoDao;", "sessionRepository", "Lcom/inspekpro/data/repository/InspectionSessionRepository;", "(Lcom/inspekpro/data/local/dao/InspectionFindingDao;Lcom/inspekpro/data/local/dao/FindingPhotoDao;Lcom/inspekpro/data/repository/InspectionSessionRepository;)V", "addFinding", "", "finding", "Lcom/inspekpro/data/local/entity/InspectionFindingEntity;", "(Lcom/inspekpro/data/local/entity/InspectionFindingEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addPhoto", "photo", "Lcom/inspekpro/data/local/entity/FindingPhotoEntity;", "(Lcom/inspekpro/data/local/entity/FindingPhotoEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteFinding", "", "findingId", "sessionId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deletePhoto", "photoId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCategoriesBySession", "Lkotlinx/coroutines/flow/Flow;", "", "", "getCriticalCount", "", "getFindingById", "id", "getFindingsBySession", "getFindingsBySeverity", "severity", "Lcom/inspekpro/data/local/entity/FindingSeverity;", "getPhotoCountByFinding", "getPhotosByFinding", "getRecentFindings", "limit", "getTotalFindingsCount", "markFindingResult", "result", "Lcom/inspekpro/data/local/entity/FindingResult;", "(JLcom/inspekpro/data/local/entity/FindingResult;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateFinding", "updateFindingStatus", "status", "Lcom/inspekpro/data/local/entity/FindingStatus;", "(JLcom/inspekpro/data/local/entity/FindingStatus;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class FindingRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.local.dao.InspectionFindingDao findingDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.local.dao.FindingPhotoDao photoDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.repository.InspectionSessionRepository sessionRepository = null;
    
    public FindingRepository(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.dao.InspectionFindingDao findingDao, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.dao.FindingPhotoDao photoDao, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.repository.InspectionSessionRepository sessionRepository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addFinding(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.InspectionFindingEntity finding, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateFinding(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.InspectionFindingEntity finding, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteFinding(long findingId, long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> getFindingsBySession(long sessionId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.inspekpro.data.local.entity.InspectionFindingEntity> getFindingById(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> getFindingsBySeverity(long sessionId, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingSeverity severity) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markFindingResult(long findingId, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingResult result, long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateFindingStatus(long findingId, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingStatus status, long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addPhoto(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingPhotoEntity photo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deletePhoto(long photoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.FindingPhotoEntity>> getPhotosByFinding(long findingId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getPhotoCountByFinding(long findingId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalFindingsCount(long sessionId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getCriticalCount(long sessionId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getCategoriesBySession(long sessionId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> getRecentFindings(int limit) {
        return null;
    }
}