package com.inspekpro.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.inspekpro.data.local.dao.ChecklistDao
import com.inspekpro.data.local.dao.FindingPhotoDao
import com.inspekpro.data.local.dao.InspectionFindingDao
import com.inspekpro.data.local.dao.InspectionSessionDao
import com.inspekpro.data.local.dao.SessionSummaryDao
import com.inspekpro.data.local.entity.ChecklistItemEntity
import com.inspekpro.data.local.entity.ChecklistTemplateEntity
import com.inspekpro.data.local.entity.FindingPhotoEntity
import com.inspekpro.data.local.entity.FindingResult
import com.inspekpro.data.local.entity.FindingSeverity
import com.inspekpro.data.local.entity.FindingStatus
import com.inspekpro.data.local.entity.InspectionFindingEntity
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.data.local.entity.SessionStatus
import com.inspekpro.data.local.entity.SessionSummaryEntity

@Database(
    entities = [
        InspectionSessionEntity::class,
        InspectionFindingEntity::class,
        ChecklistTemplateEntity::class,
        ChecklistItemEntity::class,
        FindingPhotoEntity::class,
        SessionSummaryEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(InspekProConverters::class)
abstract class InspekProDatabase : RoomDatabase() {

    abstract fun inspectionSessionDao(): InspectionSessionDao
    abstract fun inspectionFindingDao(): InspectionFindingDao
    abstract fun checklistDao(): ChecklistDao
    abstract fun findingPhotoDao(): FindingPhotoDao
    abstract fun sessionSummaryDao(): SessionSummaryDao

    companion object {
        private const val DATABASE_NAME = "inspekpro.db"

        @Volatile
        private var INSTANCE: InspekProDatabase? = null

        fun getInstance(context: Context): InspekProDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    InspekProDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

@Suppress("unused")
class InspekProConverters {

    @TypeConverter
    fun fromSessionStatus(value: SessionStatus?): String? {
        return value?.name
    }

    @TypeConverter
    fun toSessionStatus(value: String?): SessionStatus? {
        return value?.let { SessionStatus.valueOf(it) }
    }

    @TypeConverter
    fun fromFindingSeverity(value: FindingSeverity?): String? {
        return value?.name
    }

    @TypeConverter
    fun toFindingSeverity(value: String?): FindingSeverity? {
        return value?.let { FindingSeverity.valueOf(it) }
    }

    @TypeConverter
    fun fromFindingStatus(value: FindingStatus?): String? {
        return value?.name
    }

    @TypeConverter
    fun toFindingStatus(value: String?): FindingStatus? {
        return value?.let { FindingStatus.valueOf(it) }
    }

    @TypeConverter
    fun fromFindingResult(value: FindingResult?): String? {
        return value?.name
    }

    @TypeConverter
    fun toFindingResult(value: String?): FindingResult? {
        return value?.let { FindingResult.valueOf(it) }
    }
}