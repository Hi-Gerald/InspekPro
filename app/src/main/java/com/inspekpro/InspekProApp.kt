package com.inspekpro

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InspekProApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inisialisasi Firebase secara manual jika plugin google-services tidak aktif
        // atau jika google-services.json belum ditambahkan.
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            // Abaikan jika gagal (misal: config missing), AppModule akan menangani null check
        }
    }
}
