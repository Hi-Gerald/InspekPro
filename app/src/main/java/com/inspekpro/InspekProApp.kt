package com.inspekpro

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

@HiltAndroidApp
class InspekProApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}