package com.inspekpro.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.inspekpro.data.repository.AuthRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LogoutService : Service() {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        // Logout user when the task is removed (swiped away from recents)
        // Using runBlocking to ensure the logout operation finishes before the process is killed
        kotlinx.coroutines.runBlocking {
            authRepository.logoutUser()
        }
        stopSelf()
    }
}
