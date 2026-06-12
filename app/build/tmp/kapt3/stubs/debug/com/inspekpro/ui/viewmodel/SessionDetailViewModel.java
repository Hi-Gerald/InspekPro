package com.inspekpro.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0010J\u0006\u0010\u001f\u001a\u00020\u001dJ\u0016\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020#J\u0006\u0010$\u001a\u00020\u001dR\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0019\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000eR\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u000e\u00a8\u0006%"}, d2 = {"Lcom/inspekpro/ui/viewmodel/SessionDetailViewModel;", "Landroidx/lifecycle/ViewModel;", "sessionRepo", "Lcom/inspekpro/data/repository/InspectionSessionRepository;", "findingRepo", "Lcom/inspekpro/data/repository/FindingRepository;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "(Lcom/inspekpro/data/repository/InspectionSessionRepository;Lcom/inspekpro/data/repository/FindingRepository;Landroidx/lifecycle/SavedStateHandle;)V", "categories", "Lkotlinx/coroutines/flow/Flow;", "", "", "getCategories", "()Lkotlinx/coroutines/flow/Flow;", "criticalFindings", "Lcom/inspekpro/data/local/entity/InspectionFindingEntity;", "getCriticalFindings", "findings", "getFindings", "session", "Lcom/inspekpro/data/local/entity/InspectionSessionEntity;", "getSession", "sessionId", "", "summary", "Lcom/inspekpro/data/local/entity/SessionSummaryEntity;", "getSummary", "addFinding", "", "finding", "completeSession", "markFindingResult", "findingId", "result", "Lcom/inspekpro/data/local/entity/FindingResult;", "startSession", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SessionDetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.repository.InspectionSessionRepository sessionRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.repository.FindingRepository findingRepo = null;
    private final long sessionId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.inspekpro.data.local.entity.InspectionSessionEntity> session = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.inspekpro.data.local.entity.SessionSummaryEntity> summary = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> findings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> criticalFindings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> categories = null;
    
    @javax.inject.Inject()
    public SessionDetailViewModel(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.repository.InspectionSessionRepository sessionRepo, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.repository.FindingRepository findingRepo, @org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.inspekpro.data.local.entity.InspectionSessionEntity> getSession() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.inspekpro.data.local.entity.SessionSummaryEntity> getSummary() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> getFindings() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> getCriticalFindings() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getCategories() {
        return null;
    }
    
    public final void startSession() {
    }
    
    public final void completeSession() {
    }
    
    public final void addFinding(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.InspectionFindingEntity finding) {
    }
    
    public final void markFindingResult(long findingId, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.FindingResult result) {
    }
}