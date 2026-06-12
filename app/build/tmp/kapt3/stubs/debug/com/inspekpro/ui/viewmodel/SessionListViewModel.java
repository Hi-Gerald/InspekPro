package com.inspekpro.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u0010\u001a\u00020\u0007R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001d\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013\u00a8\u0006\u001c"}, d2 = {"Lcom/inspekpro/ui/viewmodel/SessionListViewModel;", "Landroidx/lifecycle/ViewModel;", "sessionRepo", "Lcom/inspekpro/data/repository/InspectionSessionRepository;", "(Lcom/inspekpro/data/repository/InspectionSessionRepository;)V", "_filter", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/inspekpro/ui/viewmodel/DateFilter;", "activeSessions", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/inspekpro/data/local/entity/InspectionSessionEntity;", "getActiveSessions", "()Lkotlinx/coroutines/flow/Flow;", "allSessions", "getAllSessions", "filter", "Lkotlinx/coroutines/flow/StateFlow;", "getFilter", "()Lkotlinx/coroutines/flow/StateFlow;", "sessionsByStatus", "Lcom/inspekpro/ui/viewmodel/SessionGroupUiState;", "getSessionsByStatus", "deleteSession", "", "sessionId", "", "setFilter", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SessionListViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.repository.InspectionSessionRepository sessionRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.inspekpro.ui.viewmodel.DateFilter> _filter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.inspekpro.ui.viewmodel.DateFilter> filter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> allSessions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.inspekpro.ui.viewmodel.SessionGroupUiState> sessionsByStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> activeSessions = null;
    
    @javax.inject.Inject()
    public SessionListViewModel(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.repository.InspectionSessionRepository sessionRepo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.inspekpro.ui.viewmodel.DateFilter> getFilter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> getAllSessions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.inspekpro.ui.viewmodel.SessionGroupUiState> getSessionsByStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> getActiveSessions() {
        return null;
    }
    
    public final void setFilter(@org.jetbrains.annotations.NotNull()
    com.inspekpro.ui.viewmodel.DateFilter filter) {
    }
    
    public final void deleteSession(long sessionId) {
    }
}