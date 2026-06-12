package com.inspekpro.data.local.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&\u00a8\u0006\u0010"}, d2 = {"Lcom/inspekpro/data/local/database/InspekProDatabase;", "Landroidx/room/RoomDatabase;", "()V", "checklistDao", "Lcom/inspekpro/data/local/dao/ChecklistDao;", "findingPhotoDao", "Lcom/inspekpro/data/local/dao/FindingPhotoDao;", "inspectionFindingDao", "Lcom/inspekpro/data/local/dao/InspectionFindingDao;", "inspectionSessionDao", "Lcom/inspekpro/data/local/dao/InspectionSessionDao;", "sessionSummaryDao", "Lcom/inspekpro/data/local/dao/SessionSummaryDao;", "userDao", "Lcom/inspekpro/data/local/dao/UserDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.inspekpro.data.local.entity.InspectionSessionEntity.class, com.inspekpro.data.local.entity.InspectionFindingEntity.class, com.inspekpro.data.local.entity.ChecklistTemplateEntity.class, com.inspekpro.data.local.entity.ChecklistItemEntity.class, com.inspekpro.data.local.entity.FindingPhotoEntity.class, com.inspekpro.data.local.entity.SessionSummaryEntity.class, com.inspekpro.data.local.entity.UserEntity.class}, version = 1, exportSchema = true)
@androidx.room.TypeConverters(value = {com.inspekpro.data.local.database.InspekProConverters.class})
public abstract class InspekProDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DATABASE_NAME = "inspekpro.db";
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.inspekpro.data.local.database.InspekProDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.inspekpro.data.local.database.InspekProDatabase.Companion Companion = null;
    
    public InspekProDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.inspekpro.data.local.dao.InspectionSessionDao inspectionSessionDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.inspekpro.data.local.dao.InspectionFindingDao inspectionFindingDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.inspekpro.data.local.dao.ChecklistDao checklistDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.inspekpro.data.local.dao.FindingPhotoDao findingPhotoDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.inspekpro.data.local.dao.SessionSummaryDao sessionSummaryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.inspekpro.data.local.dao.UserDao userDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/inspekpro/data/local/database/InspekProDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "INSTANCE", "Lcom/inspekpro/data/local/database/InspekProDatabase;", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.inspekpro.data.local.database.InspekProDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}