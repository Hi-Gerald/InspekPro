package com.inspekpro.ui.viewmodel

import androidx.lifecycle.*
import com.inspekpro.data.local.entity.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.inspekpro.data.remote.model.WeatherInfo
import com.inspekpro.data.repository.FindingRepository
import com.inspekpro.data.repository.InspectionSessionRepository
import com.inspekpro.data.repository.FirestoreSyncRepository
import com.inspekpro.receiver.AlarmScheduler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// ─────────────────────────────────────────────────────────────────────────────
// DASHBOARD VIEW MODEL
// ─────────────────────────────────────────────────────────────────────────────

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sessionRepo: InspectionSessionRepository,
    private val findingRepo: FindingRepository
) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weather: StateFlow<WeatherUiState> = _weather.asStateFlow()

    val totalSessions = sessionRepo.getTotalSessionCount()
    val completedSessions = sessionRepo.getCompletedCount()
    val activeSessions = sessionRepo.getAllSessions()
    val recentFindings = findingRepo.getRecentFindings(10)

    init {
        viewModelScope.launch {
            try {
                val sessions = sessionRepo.getAllSessions().first()
                if (sessions.isEmpty()) {
                    populateMockData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun populateMockData() {
        // ... (Kode mock data tetap sama)
    }

    fun loadWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _weather.value = WeatherUiState.Loading
            val result = sessionRepo.fetchWeatherByCity("Jakarta")
            result.fold(
                onSuccess = { _weather.value = WeatherUiState.Success(it) },
                onFailure = { _weather.value = WeatherUiState.Error(it.message ?: "Gagal memuat cuaca") }
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Bagian Billy: Create Session ViewModel
// Fitur: Validasi input jadwal, Integrasi AlarmManager, & Sync Firestore
// Untuk: Menangani logika bisnis pembuatan jadwal inspeksi baru dan sinkronisasi data.
// ─────────────────────────────────────────────────────────────────────────────

@HiltViewModel
class CreateSessionViewModel @Inject constructor(
    private val sessionRepo: InspectionSessionRepository,
    private val findingRepo: FindingRepository,
    private val alarmScheduler: AlarmScheduler,
    private val firestoreSyncRepo: FirestoreSyncRepository
) : ViewModel() {

    val title = MutableStateFlow("")
    val locationName = MutableStateFlow("")
    val inspectorName = MutableStateFlow("")
    val scheduledDate = MutableStateFlow(System.currentTimeMillis())
    val notes = MutableStateFlow("")
    val photos = MutableStateFlow<List<String>>(emptyList())
    val videoPath = MutableStateFlow<String?>(null)
    
    // Finding Details
    val hasFindings = MutableStateFlow(false)
    val findingCategory = MutableStateFlow("")
    val priority = MutableStateFlow("")
    val findingDescription = MutableStateFlow("")
    val findingPhotos = MutableStateFlow<List<String>>(emptyList())
    
    // Inspection Conclusion
    val conclusion = MutableStateFlow("")

    private val _createResult = MutableStateFlow<CreateSessionResult>(CreateSessionResult.Idle)
    val createResult: StateFlow<CreateSessionResult> = _createResult.asStateFlow()

    val isFormValid: StateFlow<Boolean> = combine(title, locationName, inspectorName) { t, l, i ->
        t.isNotBlank() && l.isNotBlank() && i.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun createSession(
        inspectorId: String, 
        status: SessionStatus = SessionStatus.DRAFT,
        manualTitle: String? = null,
        manualLocation: String? = null,
        manualInspector: String? = null,
        manualConclusion: String? = null,
        manualPhotos: List<String>? = null,
        manualVideo: String? = null
    ) {
        viewModelScope.launch {
            val finalTitle = manualTitle ?: title.value
            val finalLocation = manualLocation ?: locationName.value
            val finalInspector = manualInspector ?: inspectorName.value
            val finalConclusion = manualConclusion ?: conclusion.value
            val finalPhotos = manualPhotos ?: photos.value
            val finalVideo = manualVideo ?: videoPath.value

            if (finalTitle.isBlank() || finalLocation.isBlank() || finalInspector.isBlank()) {
                _createResult.value = CreateSessionResult.Error("Harap isi field wajib: Judul, Lokasi, dan Inspector")
                return@launch
            }

            _createResult.value = CreateSessionResult.Loading
            try {
                val dateStr = SimpleDateFormat("yyyyMMdd-HHmm", Locale.getDefault()).format(Date())
                val code = "INS-$dateStr"

                val newSession = InspectionSessionEntity(
                    sessionCode   = code,
                    title         = finalTitle.trim(),
                    locationName  = finalLocation.trim(),
                    inspectorName = finalInspector.trim(),
                    inspectorId   = inspectorId,
                    scheduledDate = scheduledDate.value,
                    notes         = finalConclusion.trim(),
                    reportVideoPath = finalVideo ?: finalPhotos.firstOrNull { it.endsWith(".mp4") },
                    status        = status
                )

                val sessionId = sessionRepo.createSession(newSession)
                
                // Add finding if exists
                if (hasFindings.value) {
                    val finding = InspectionFindingEntity(
                        sessionId = sessionId,
                        findingCode = "FND-${System.currentTimeMillis() % 10000}",
                        category = findingCategory.value,
                        title = "Temuan ${title.value}",
                        description = findingDescription.value,
                        severity = when(priority.value.lowercase()) {
                            "high", "tinggi", "kritis" -> FindingSeverity.CRITICAL
                            "medium", "sedang" -> FindingSeverity.MAJOR
                            else -> FindingSeverity.MINOR
                        },
                        status = FindingStatus.OPEN,
                        photoPaths = findingPhotos.value.joinToString(",")
                    )
                    val findingId = findingRepo.addFinding(finding)
                    
                    // Add finding photos to FindingPhotoEntity as well
                    findingPhotos.value.forEach { path ->
                        findingRepo.addPhoto(FindingPhotoEntity(
                            findingId = findingId,
                            localPath = path
                        ))
                    }
                }

                // Jadwalkan Pengingat (AlarmManager)
                alarmScheduler.schedule(newSession.copy(sessionId = sessionId))

                // Sinkron ke Cloud (Firestore)
                firestoreSyncRepo.syncUnsyncedSessions()

                _createResult.value = CreateSessionResult.Success(sessionId)
            } catch (e: Exception) {
                _createResult.value = CreateSessionResult.Error(e.message ?: "Gagal membuat sesi")
            }
        }
    }

    fun resetForm() {
        title.value = ""
        locationName.value = ""
        notes.value = ""
        videoPath.value = null
        _createResult.value = CreateSessionResult.Idle
    }
}

sealed class CreateSessionResult {
    object Idle : CreateSessionResult()
    object Loading : CreateSessionResult()
    data class Success(val sessionId: Long) : CreateSessionResult()
    data class Error(val message: String) : CreateSessionResult()
}

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val data: WeatherInfo) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

@HiltViewModel
class SessionListViewModel @Inject constructor(
    private val sessionRepo: InspectionSessionRepository
) : ViewModel() {
    private val _filter = MutableStateFlow(DateFilter.ALL)
    val filter: StateFlow<DateFilter> = _filter.asStateFlow()

    private val _selectedDateMillis = MutableStateFlow(System.currentTimeMillis())
    val selectedDateMillis: StateFlow<Long> = _selectedDateMillis.asStateFlow()

    val allSessions = sessionRepo.getAllSessions()

    val filteredSessions: Flow<List<InspectionSessionEntity>> = combine(allSessions, _selectedDateMillis) { sessions, selectedMillis ->
        val cal = Calendar.getInstance().apply { timeInMillis = selectedMillis }
        val day = cal.get(Calendar.DAY_OF_YEAR)
        val year = cal.get(Calendar.YEAR)

        sessions.filter {
            val sessionCal = Calendar.getInstance().apply { timeInMillis = it.scheduledDate }
            sessionCal.get(Calendar.DAY_OF_YEAR) == day && sessionCal.get(Calendar.YEAR) == year
        }
    }

    val sessionsByStatus: StateFlow<SessionGroupUiState> = allSessions.map { sessions ->
        SessionGroupUiState(
            total = sessions.size,
            selesai = sessions.count { it.status == SessionStatus.COMPLETED },
            proses = sessions.count { it.status == SessionStatus.IN_PROGRESS },
            tertunda = sessions.count { it.status == SessionStatus.DRAFT }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SessionGroupUiState())
    
    fun setSelectedDate(millis: Long) {
        _selectedDateMillis.value = millis
    }

    fun setFilter(filter: DateFilter) { _filter.value = filter }
    fun deleteSession(sessionId: Long) { viewModelScope.launch { sessionRepo.deleteSession(sessionId) } }
}

data class SessionGroupUiState(val total: Int = 0, val selesai: Int = 0, val proses: Int = 0, val tertunda: Int = 0)
enum class DateFilter { TODAY, THIS_WEEK, THIS_MONTH, ALL }

@HiltViewModel
class SessionDetailViewModel @Inject constructor(
    private val sessionRepo: InspectionSessionRepository,
    private val findingRepo: FindingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val sessionId: Long = savedStateHandle.get<Long>("sessionId") ?: -1L
    val session = sessionRepo.getSessionById(sessionId)
    val summary = sessionRepo.getSessionSummary(sessionId)
    val findings = findingRepo.getFindingsBySession(sessionId)
    val criticalFindings = findingRepo.getFindingsBySeverity(sessionId, FindingSeverity.CRITICAL)
    val categories = findingRepo.getCategoriesBySession(sessionId)
    fun startSession() { viewModelScope.launch { sessionRepo.startSession(sessionId) } }
    fun completeSession() { viewModelScope.launch { sessionRepo.completeSession(sessionId) } }
    fun addFinding(finding: InspectionFindingEntity) { viewModelScope.launch { findingRepo.addFinding(finding) } }
    fun markFindingResult(findingId: Long, result: FindingResult) { viewModelScope.launch { findingRepo.markFindingResult(findingId, result, sessionId) } }
}
