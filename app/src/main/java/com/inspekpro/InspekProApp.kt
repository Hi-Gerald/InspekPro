package com.inspekpro

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

@HiltAndroidApp
class InspekProApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Cek apakah Firebase sudah terinisialisasi (misal via google-services.json)
        // Jika belum (karena belum ada google-services.json), inisialisasi dengan dummy
        if (FirebaseApp.getApps(this).isEmpty()) {
            val options = FirebaseOptions.Builder()
                .setProjectId("dummy-project-id")
                .setApplicationId("1:1234567890:android:abcdef123456")
                .setApiKey("dummy-api-key-1234567890")
                .build()
            FirebaseApp.initializeApp(this, options)
        }
    }
}