package com.inspekpro.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inspekpro.data.local.database.InspekProDatabase
import com.inspekpro.data.remote.api.RetrofitClient
import com.inspekpro.data.remote.api.WeatherApiService
import com.inspekpro.data.repository.FindingRepository
import com.inspekpro.data.repository.InspectionSessionRepository
import com.inspekpro.data.repository.AuthRepository
import com.inspekpro.receiver.AlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ─── DATABASE ─────────────────────────────────────────────────────────────

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): InspekProDatabase =
        InspekProDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideSessionDao(db: InspekProDatabase) = db.inspectionSessionDao()

    @Singleton
    @Provides
    fun provideFindingDao(db: InspekProDatabase) = db.inspectionFindingDao()

    @Singleton
    @Provides
    fun provideChecklistDao(db: InspekProDatabase) = db.checklistDao()

    @Singleton
    @Provides
    fun providePhotoDao(db: InspekProDatabase) = db.findingPhotoDao()

    @Singleton
    @Provides
    fun provideSummaryDao(db: InspekProDatabase) = db.sessionSummaryDao()

    @Singleton
    @Provides
    fun provideUserDao(db: InspekProDatabase) = db.userDao()

    // ─── NETWORK ──────────────────────────────────────────────────────────────

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApiService = RetrofitClient.weatherApiService

    /**
     * Bagian Billy: Provider Firebase Firestore
     * Digunakan untuk sinkronisasi data jadwal inspeksi ke Cloud.
     */
    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    // ─── REPOSITORIES ─────────────────────────────────────────────────────────

    /**
     * Bagian Billy: Provider Session Repository
     * Digunakan untuk manajemen data jadwal inspeksi di lokal (Room).
     */
    @Singleton
    @Provides
    fun provideSessionRepository(
        db: InspekProDatabase,
        weatherApi: WeatherApiService
    ): InspectionSessionRepository = InspectionSessionRepository(
        sessionDao  = db.inspectionSessionDao(),
        findingDao  = db.inspectionFindingDao(),
        summaryDao  = db.sessionSummaryDao(),
        weatherApi  = weatherApi
    )

    @Singleton
    @Provides
    fun provideFindingRepository(
        db: InspekProDatabase,
        sessionRepo: InspectionSessionRepository
    ): FindingRepository = FindingRepository(
        findingDao       = db.inspectionFindingDao(),
        photoDao         = db.findingPhotoDao(),
        sessionRepository = sessionRepo
    )

    @Singleton
    @Provides
    fun provideAuthRepository(db: InspekProDatabase): AuthRepository = AuthRepository(
        userDao = db.userDao()
    )

    // ─── UTILS ────────────────────────────────────────────────────────────────

    /**
     * Bagian Billy: Provider Alarm Scheduler
     * Digunakan untuk menjadwalkan notifikasi pengingat inspeksi.
     */
    @Singleton
    @Provides
    fun provideAlarmScheduler(@ApplicationContext context: Context): AlarmScheduler =
        AlarmScheduler(context)
}
