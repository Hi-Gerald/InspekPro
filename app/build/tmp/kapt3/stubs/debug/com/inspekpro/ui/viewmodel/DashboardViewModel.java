package com.inspekpro.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fJ\u0016\u0010!\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fJ\u000e\u0010\"\u001a\u00020\u001dH\u0082@\u00a2\u0006\u0002\u0010#R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006$"}, d2 = {"Lcom/inspekpro/ui/viewmodel/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "sessionRepo", "Lcom/inspekpro/data/repository/InspectionSessionRepository;", "findingRepo", "Lcom/inspekpro/data/repository/FindingRepository;", "(Lcom/inspekpro/data/repository/InspectionSessionRepository;Lcom/inspekpro/data/repository/FindingRepository;)V", "_weather", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/inspekpro/ui/viewmodel/WeatherUiState;", "activeSessions", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/inspekpro/data/local/entity/InspectionSessionEntity;", "getActiveSessions", "()Lkotlinx/coroutines/flow/Flow;", "completedSessions", "", "getCompletedSessions", "recentFindings", "Lcom/inspekpro/data/local/entity/InspectionFindingEntity;", "getRecentFindings", "totalSessions", "getTotalSessions", "weather", "Lkotlinx/coroutines/flow/StateFlow;", "getWeather", "()Lkotlinx/coroutines/flow/StateFlow;", "loadWeather", "", "lat", "", "lon", "loadWeatherByCoords", "populateMockData", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.repository.InspectionSessionRepository sessionRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.repository.FindingRepository findingRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.inspekpro.ui.viewmodel.WeatherUiState> _weather = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.inspekpro.ui.viewmodel.WeatherUiState> weather = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> totalSessions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> completedSessions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> activeSessions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> recentFindings = null;
    
    @javax.inject.Inject()
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.repository.InspectionSessionRepository sessionRepo, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.repository.FindingRepository findingRepo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.inspekpro.ui.viewmodel.WeatherUiState> getWeather() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalSessions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getCompletedSessions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> getActiveSessions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionFindingEntity>> getRecentFindings() {
        return null;
    }
    
    private final java.lang.Object populateMockData(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void loadWeather(double lat, double lon) {
    }
    
    public final void loadWeatherByCoords(double lat, double lon) {
    }
}