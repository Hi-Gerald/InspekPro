package com.inspekpro.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.inspekpro.data.local.entity.InspectionSessionEntity

/**
 * Bagian Billy: Alarm Scheduler
 * Fitur: Penjadwalan AlarmManager (Exact Alarm)
 * Tujuan: Mengatur agar sistem memberikan sinyal ke BroadcastReceiver tepat pada waktu inspeksi yang dijadwalkan.
 */
class AlarmScheduler(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(session: InspectionSessionEntity) {
        val intent = Intent(context, InspectionReminderReceiver::class.java).apply {
            putExtra("EXTRA_TITLE", "Jadwal Inspeksi: ${session.title}")
            putExtra("EXTRA_MESSAGE", "Lokasi: ${session.locationName}")
            putExtra("EXTRA_SESSION_ID", session.sessionId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            session.sessionId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            session.scheduledDate,
            pendingIntent
        )
    }

    fun cancel(sessionId: Long) {
        val intent = Intent(context, InspectionReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            sessionId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}
