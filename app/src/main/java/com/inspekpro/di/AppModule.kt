package com.inspekpro.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.inspekpro.data.local.database.InspekProDatabase
<<<<<<< HEAD
=======
import com.inspekpro.data.remote.api.RetrofitClient
import com.inspekpro.data.remote.api.WeatherApiService
>>>>>>> 7fbb84acde950e4fdc1c6617dc2a9cf6ad421f54
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

<<<<<<< HEAD
=======
    // ─── NETWORK ──────────────────────────────────────────────────────────────

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApiService = RetrofitClient.weatherApiService

>>>>>>> 7fbb84acde950e4fdc1c6617dc2a9cf6ad421f54
    /**
     * Bagian Billy: Provider Firebase Firestore
     * Digunakan untuk sinkronisasi data jadwal inspeksi ke Cloud.
     * Ditambahkan Try-Catch agar tidak crash jika google-services.json belum ada.
     */
    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore? = try {
        // Coba ambil instance default, jika gagal (misal: config missing) akan lempar IllegalStateException
        FirebaseFirestore.getInstance()
    } catch (e: Exception) {
        null
    }

    /**
     * Bagian Billy: Provider Firebase Auth
     * Digunakan untuk manajemen login & registrasi ke Cloud.
     * Ditambahkan Try-Catch agar tidak crash jika google-services.json belum ada.
     */
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth? = try {
        FirebaseAuth.getInstance()
    } catch (e: Exception) {
        null
    }

    // ─── REPOSITORIES ─────────────────────────────────────────────────────────

    /**
     * Bagian Billy: Provider Session Repository
     * Digunakan untuk manajemen data jadwal inspeksi di lokal (Room).
     */
    @Singleton
    @Provides
    fun provideSessionRepository(
        db: InspekProDatabase,
<<<<<<< HEAD
        weatherRepo: com.inspekpro.data.repository.WeatherRepository
=======
        weatherApi: WeatherApiService
>>>>>>> 7fbb84acde950e4fdc1c6617dc2a9cf6ad421f54
    ): InspectionSessionRepository = InspectionSessionRepository(
        sessionDao  = db.inspectionSessionDao(),
        findingDao  = db.inspectionFindingDao(),
        summaryDao  = db.sessionSummaryDao(),
<<<<<<< HEAD
        weatherRepo = weatherRepo
=======
        weatherApi  = weatherApi
>>>>>>> 7fbb84acde950e4fdc1c6617dc2a9cf6ad421f54
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
    fun provideAuthRepository(
        db: InspekProDatabase,
        firebaseAuth: FirebaseAuth?
    ): AuthRepository = AuthRepository(
        userDao = db.userDao(),
        firebaseAuth = firebaseAuth
    )

    @Singleton
    @Provides
    fun provideWeatherRepository(): com.inspekpro.data.repository.WeatherRepository = 
        com.inspekpro.data.repository.BmkgWeatherRepositoryImpl()

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
