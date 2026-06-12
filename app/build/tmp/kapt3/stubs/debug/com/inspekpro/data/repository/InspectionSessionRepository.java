package com.inspekpro.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ4\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cJ$\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u001e\u001a\u00020\u001fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b \u0010!J\u0012\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120$0#J\u0012\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120$0#J\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0#J\u000e\u0010(\u001a\u00020)H\u0086@\u00a2\u0006\u0002\u0010*J\u0016\u0010+\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120#2\u0006\u0010,\u001a\u00020\u000eJ\u0016\u0010-\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010.0#2\u0006\u0010\r\u001a\u00020\u000eJ\u001a\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120$0#2\u0006\u00100\u001a\u000201J\f\u00102\u001a\b\u0012\u0004\u0012\u00020\'0#J\u0016\u00103\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u001a\u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120$0#2\u0006\u00105\u001a\u00020\u001fJ\u0016\u00106\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u00107\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00068"}, d2 = {"Lcom/inspekpro/data/repository/InspectionSessionRepository;", "", "sessionDao", "Lcom/inspekpro/data/local/dao/InspectionSessionDao;", "findingDao", "Lcom/inspekpro/data/local/dao/InspectionFindingDao;", "summaryDao", "Lcom/inspekpro/data/local/dao/SessionSummaryDao;", "weatherApi", "Lcom/inspekpro/data/remote/api/WeatherApiService;", "(Lcom/inspekpro/data/local/dao/InspectionSessionDao;Lcom/inspekpro/data/local/dao/InspectionFindingDao;Lcom/inspekpro/data/local/dao/SessionSummaryDao;Lcom/inspekpro/data/remote/api/WeatherApiService;)V", "completeSession", "", "sessionId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createSession", "session", "Lcom/inspekpro/data/local/entity/InspectionSessionEntity;", "(Lcom/inspekpro/data/local/entity/InspectionSessionEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSession", "fetchAndAttachWeather", "Lkotlin/Result;", "Lcom/inspekpro/data/remote/model/WeatherInfo;", "lat", "", "lon", "fetchAndAttachWeather-BWLJW6A", "(JDDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchWeatherByCity", "cityName", "", "fetchWeatherByCity-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveSessions", "Lkotlinx/coroutines/flow/Flow;", "", "getAllSessions", "getCompletedCount", "", "getDashboardStats", "Lcom/inspekpro/data/local/dao/SessionSummaryDao$DashboardStats;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSessionById", "id", "getSessionSummary", "Lcom/inspekpro/data/local/entity/SessionSummaryEntity;", "getSessionsByStatus", "status", "Lcom/inspekpro/data/local/entity/SessionStatus;", "getTotalSessionCount", "refreshSummary", "searchSessions", "query", "startSession", "updateSession", "app_debug"})
public final class InspectionSessionRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.local.dao.InspectionSessionDao sessionDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.local.dao.InspectionFindingDao findingDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.local.dao.SessionSummaryDao summaryDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.remote.api.WeatherApiService weatherApi = null;
    
    public InspectionSessionRepository(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.dao.InspectionSessionDao sessionDao, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.dao.InspectionFindingDao findingDao, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.dao.SessionSummaryDao summaryDao, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.remote.api.WeatherApiService weatherApi) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createSession(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.InspectionSessionEntity session, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateSession(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.InspectionSessionEntity session, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> getAllSessions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.inspekpro.data.local.entity.InspectionSessionEntity> getSessionById(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> getSessionsByStatus(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.entity.SessionStatus status) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> searchSessions(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object startSession(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object completeSession(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteSession(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.inspekpro.data.local.entity.SessionSummaryEntity> getSessionSummary(long sessionId) {
        return null;
    }
    
    /**
     * Hitung ulang ringkasan temuan untuk satu sesi.
     * Dipanggil setiap kali finding berubah.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object refreshSummary(long sessionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.inspekpro.data.local.entity.InspectionSessionEntity>> getActiveSessions() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getDashboardStats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.inspekpro.data.local.dao.SessionSummaryDao.DashboardStats> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalSessionCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getCompletedCount() {
        return null;
    }
}