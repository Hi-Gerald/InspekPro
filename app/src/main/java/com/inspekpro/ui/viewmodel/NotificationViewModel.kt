package com.inspekpro.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.data.repository.InspectionSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val sessionRepository: InspectionSessionRepository
) : ViewModel() {

    val upcomingInspections: StateFlow<List<InspectionSessionEntity>> = sessionRepository
        .getUpcomingSessions(7)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
