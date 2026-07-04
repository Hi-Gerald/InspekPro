package com.inspekpro.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.data.local.entity.SessionStatus
import com.inspekpro.data.repository.InspectionSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Bagian Billy: ViewModel untuk Notifikasi
 * Fitur: Mengambil data jadwal inspeksi 7 hari ke depan dari Room
 */
@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val sessionRepo: InspectionSessionRepository
) : ViewModel() {

    /**
     * Mengambil daftar jadwal yang berstatus DRAFT atau IN_PROGRESS
     * dan dijadwalkan dalam 7 hari ke depan.
     */
    val upcomingInspections: StateFlow<List<InspectionSessionEntity>> = sessionRepo.getAllSessions()
        .map { sessions ->
            val now = System.currentTimeMillis()
            val sevenDaysLater = now + (7 * 24 * 60 * 60 * 1000L)
            
            sessions.filter { session ->
                (session.status == SessionStatus.DRAFT || session.status == SessionStatus.IN_PROGRESS) &&
                        session.scheduledDate in now..sevenDaysLater
            }.sortedBy { it.scheduledDate }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
