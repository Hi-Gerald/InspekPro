package com.inspekpro.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.data.local.entity.SessionStatus
import com.inspekpro.data.repository.FindingRepository
import com.inspekpro.data.repository.InspectionSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val sessionRepo: InspectionSessionRepository,
    private val findingRepo: FindingRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    val filterLocation = MutableStateFlow("")
    val filterStartDate = MutableStateFlow<Long?>(null)
    val filterEndDate = MutableStateFlow<Long?>(null)
    val filterFindingStatus = MutableStateFlow("All") // "All", "Has Findings", "No Findings"
    val sortOption = MutableStateFlow("newest") // "newest", "oldest"

    // Raw list of completed sessions
    private val completedSessions: Flow<List<InspectionSessionEntity>> =
        sessionRepo.getSessionsByStatus(SessionStatus.COMPLETED)

    // Combined, filtered, and sorted flow of sessions
    val filteredReports: StateFlow<List<InspectionSessionEntity>> = combine(
        completedSessions,
        searchQuery,
        filterLocation,
        filterStartDate,
        filterEndDate,
        filterFindingStatus,
        sortOption
    ) { flowsArray ->
        @Suppress("UNCHECKED_CAST")
        val sessions = flowsArray[0] as List<InspectionSessionEntity>
        val query = flowsArray[1] as String
        val loc = flowsArray[2] as String
        val start = flowsArray[3] as Long?
        val end = flowsArray[4] as Long?
        val finding = flowsArray[5] as String
        val sort = flowsArray[6] as String

        var list = sessions

        // Realtime Search (Machine Name / Title, Location, Inspector)
        if (query.isNotBlank()) {
            list = list.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.locationName.contains(query, ignoreCase = true) ||
                it.inspectorName.contains(query, ignoreCase = true)
            }
        }

        // Filtering by Location
        if (loc.isNotBlank()) {
            list = list.filter {
                it.locationName.contains(loc, ignoreCase = true)
            }
        }

        // Filtering by Date Range
        if (start != null) {
            list = list.filter { it.scheduledDate >= start }
        }
        if (end != null) {
            // Include full day
            val endOfDay = end + 86400000L - 1L
            list = list.filter { it.scheduledDate <= endOfDay }
        }

        // Filtering by Finding Status
        if (finding != "All") {
            list = list.filter {
                val hasFindings = it.failedItems > 0 || (it.totalItems - it.passedItems) > 0
                if (finding == "Has Findings") hasFindings else !hasFindings
            }
        }

        // Sorting
        list = if (sort == "newest") {
            list.sortedByDescending { it.scheduledDate }
        } else {
            list.sortedBy { it.scheduledDate }
        }

        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Fetch findings for a session (used for detail view)
    fun getFindingsForSession(sessionId: Long) = findingRepo.getFindingsBySession(sessionId)

    // Fetch session details (used for detail view)
    fun getSessionById(sessionId: Long) = sessionRepo.getSessionById(sessionId)

    // Fetch session summary (used for detail view)
    fun getSessionSummary(sessionId: Long) = sessionRepo.getSessionSummary(sessionId)
}
