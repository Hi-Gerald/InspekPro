package com.inspekpro.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0018\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u001d\u001a\u00020\u0018H\u0007\u00a8\u0006\u001e"}, d2 = {"Lcom/inspekpro/di/AppModule;", "", "()V", "provideAuthRepository", "Lcom/inspekpro/data/repository/AuthRepository;", "db", "Lcom/inspekpro/data/local/database/InspekProDatabase;", "provideChecklistDao", "Lcom/inspekpro/data/local/dao/ChecklistDao;", "provideDatabase", "context", "Landroid/content/Context;", "provideFindingDao", "Lcom/inspekpro/data/local/dao/InspectionFindingDao;", "provideFindingRepository", "Lcom/inspekpro/data/repository/FindingRepository;", "sessionRepo", "Lcom/inspekpro/data/repository/InspectionSessionRepository;", "providePhotoDao", "Lcom/inspekpro/data/local/dao/FindingPhotoDao;", "provideSessionDao", "Lcom/inspekpro/data/local/dao/InspectionSessionDao;", "provideSessionRepository", "weatherApi", "Lcom/inspekpro/data/remote/api/WeatherApiService;", "provideSummaryDao", "Lcom/inspekpro/data/local/dao/SessionSummaryDao;", "provideUserDao", "Lcom/inspekpro/data/local/dao/UserDao;", "provideWeatherApi", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.inspekpro.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.database.InspekProDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.dao.InspectionSessionDao provideSessionDao(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.database.InspekProDatabase db) {
        return null;
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.dao.InspectionFindingDao provideFindingDao(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.database.InspekProDatabase db) {
        return null;
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.dao.ChecklistDao provideChecklistDao(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.database.InspekProDatabase db) {
        return null;
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.dao.FindingPhotoDao providePhotoDao(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.database.InspekProDatabase db) {
        return null;
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.dao.SessionSummaryDao provideSummaryDao(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.database.InspekProDatabase db) {
        return null;
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.local.dao.UserDao provideUserDao(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.database.InspekProDatabase db) {
        return null;
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.remote.api.WeatherApiService provideWeatherApi() {
        return null;
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.repository.InspectionSessionRepository provideSessionRepository(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.database.InspekProDatabase db, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.remote.api.WeatherApiService weatherApi) {
        return null;
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.repository.FindingRepository provideFindingRepository(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.database.InspekProDatabase db, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.repository.InspectionSessionRepository sessionRepo) {
        return null;
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.repository.AuthRepository provideAuthRepository(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.local.database.InspekProDatabase db) {
        return null;
    }
}