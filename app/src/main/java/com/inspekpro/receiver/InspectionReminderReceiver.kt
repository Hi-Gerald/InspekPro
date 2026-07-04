package com.inspekpro.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.inspekpro.MainActivity
import com.inspekpro.R

/**
 * Bagian Billy: Receiver Notifikasi Inspeksi
 * Fitur: BroadcastReceiver untuk menangkap sinyal AlarmManager.
 * Tujuan: Menampilkan notifikasi pengingat ke user ketika waktu inspeksi telah tiba.
 */
class InspectionReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("EXTRA_TITLE") ?: "Jadwal Inspeksi"
        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: "Anda memiliki jadwal inspeksi hari ini."
        val sessionId = intent.getLongExtra("EXTRA_SESSION_ID", -1L)

        showNotification(context, title, message, sessionId)
    }

    private fun showNotification(context: Context, title: String, message: String, sessionId: Long) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "inspection_reminders"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Pengingat Inspeksi",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("sessionId", sessionId)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            sessionId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(sessionId.toInt(), notification)
    }
}
