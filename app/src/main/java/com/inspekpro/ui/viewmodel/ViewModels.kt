package com.inspekpro.ui.viewmodel

import androidx.lifecycle.*
import com.inspekpro.data.local.entity.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.inspekpro.data.remote.model.WeatherInfo
import com.inspekpro.data.repository.FindingRepository
import com.inspekpro.data.repository.InspectionSessionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// ─────────────────────────────────────────────────────────────────────────────
// DASHBOARD VIEW MODEL
// Screen: Dashboard (Image 1) – cuaca, stat cards, inspeksi aktif
// ─────────────────────────────────────────────────────────────────────────────

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sessionRepo: InspectionSessionRepository,
    private val findingRepo: FindingRepository
) : ViewModel() {

    // Weather state
    private val _weather = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weather: StateFlow<WeatherUiState> = _weather.asStateFlow()

    // Stats (248 Total, 189 Selesai, 12 Temuan Kritis, 203 Laporan)
    val totalSessions = sessionRepo.getTotalSessionCount()
    val completedSessions = sessionRepo.getCompletedCount()

    // Inspeksi Aktif (In Progress & All others to display on dashboard list)
    val activeSessions = sessionRepo.getAllSessions()

    // Recent Findings
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
        try {
            val s1Id = sessionRepo.createSession(
                InspectionSessionEntity(
                    sessionId = 1,
                    sessionCode = "INS-2026-001",
                    title = "Turbine Generator Unit 2",
                    locationName = "Plant A - Section 3",
                    inspectorName = "Sofia",
                    inspectorId = "INS-001",
                    status = SessionStatus.IN_PROGRESS,
                    scheduledDate = System.currentTimeMillis() - 86400000,
                    totalItems = 4,
                    passedItems = 3,
                    failedItems = 0,
                    weatherCondition = "Berawan Sebagian",
                    weatherTempCelsius = 28.0
                )
            )

            val s2Id = sessionRepo.createSession(
                InspectionSessionEntity(
                    sessionId = 2,
                    sessionCode = "INS-2026-002",
                    title = "Pressure Vessel Tank B-301",
                    locationName = "Plant B - Section 1",
                    inspectorName = "Sofia",
                    inspectorId = "INS-001",
                    status = SessionStatus.DRAFT,
                    scheduledDate = System.currentTimeMillis(),
                    totalItems = 10,
                    passedItems = 3,
                    failedItems = 0,
                    weatherCondition = "Berawan Sebagian",
                    weatherTempCelsius = 28.0
                )
            )

            val s3Id = sessionRepo.createSession(
                InspectionSessionEntity(
                    sessionId = 3,
                    sessionCode = "INS-2026-003",
                    title = "Cooling Tower System",
                    locationName = "Plant A - Section 5",
                    inspectorName = "Sofia",
                    inspectorId = "INS-001",
                    status = SessionStatus.COMPLETED,
                    scheduledDate = System.currentTimeMillis() - 172800000,
                    totalItems = 5,
                    passedItems = 5,
                    failedItems = 0,
                    weatherCondition = "Berawan Sebagian",
                    weatherTempCelsius = 28.0
                )
            )

            findingRepo.addFinding(
                InspectionFindingEntity(
                    findingId = 1,
                    sessionId = s1Id,
                    findingCode = "F-001",
                    category = "Mechanical",
                    title = "Kebocoran Minor pada Seal Turbine",
                    description = "Kebocoran oli pelumas pada seal turbine unit 2.",
                    severity = FindingSeverity.MINOR,
                    status = FindingStatus.OPEN,
                    createdAt = System.currentTimeMillis() - 3600000
                )
            )

            findingRepo.addFinding(
                InspectionFindingEntity(
                    findingId = 2,
                    sessionId = s1Id,
                    findingCode = "F-002",
                    category = "Corrosion",
                    title = "Korosi pada Flange Connection",
                    description = "Korosi permukaan pada flange pipa kondensor.",
                    severity = FindingSeverity.MAJOR,
                    status = FindingStatus.IN_PROGRESS,
                    createdAt = System.currentTimeMillis() - 7200000
                )
            )

            findingRepo.addFinding(
                InspectionFindingEntity(
                    findingId = 3,
                    sessionId = s1Id,
                    findingCode = "F-003",
                    category = "Vibration",
                    title = "Getaran Berlebih pada Motor Pump",
                    description = "Amplitudo getaran melebihi batas toleransi pada motor pompa utama.",
                    severity = FindingSeverity.CRITICAL,
                    status = FindingStatus.OPEN,
                    createdAt = System.currentTimeMillis() - 10800000
                )
            )

            findingRepo.addFinding(
                InspectionFindingEntity(
                    findingId = 4,
                    sessionId = s3Id,
                    findingCode = "F-004",
                    category = "Structural",
                    title = "Deformasi Ringan pada Support Beam",
                    description = "Deformasi kecil terdeteksi pada kaki penyangga tower.",
                    severity = FindingSeverity.OBSERVATION,
                    status = FindingStatus.RESOLVED,
                    createdAt = System.currentTimeMillis() - 14400000
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    fun loadWeatherByCoords(lat: Double, lon: Double) {
        viewModelScope.launch {
            _weather.value = WeatherUiState.Loading
            val result = sessionRepo.fetchAndAttachWeather(-1L, lat, lon)
            result.fold(
                onSuccess = { _weather.value = WeatherUiState.Success(it) },
                onFailure = { _weather.value = WeatherUiState.Error(it.message ?: "Gagal memuat cuaca") }
            )
        }
    }
}

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val data: WeatherInfo) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

// ─────────────────────────────────────────────────────────────────────────────
// SESSION LIST VIEW MODEL
// Screen: Inspeksi (Image 2) – ringkasan + list aktif
// ─────────────────────────────────────────────────────────────────────────────

@HiltViewModel
class SessionListViewModel @Inject constructor(
    private val sessionRepo: InspectionSessionRepository
) : ViewModel() {

    // Filter: "Hari ini", "Minggu ini", "Bulan ini"
    private val _filter = MutableStateFlow(DateFilter.TODAY)
    val filter: StateFlow<DateFilter> = _filter.asStateFlow()

    val allSessions = sessionRepo.getAllSessions()

    val sessionsByStatus: StateFlow<SessionGroupUiState> = allSessions
        .map { sessions ->
            SessionGroupUiState(
                total      = sessions.size,
                selesai    = sessions.count { it.status == SessionStatus.COMPLETED },
                proses     = sessions.count { it.status == SessionStatus.IN_PROGRESS },
                tertunda   = sessions.count { it.status == SessionStatus.DRAFT }
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SessionGroupUiState())

    val activeSessions: Flow<List<InspectionSessionEntity>> = allSessions.map { list ->
        list.filter { it.status in listOf(SessionStatus.IN_PROGRESS, SessionStatus.DRAFT) }
    }

    fun setFilter(filter: DateFilter) {
        _filter.value = filter
    }

    fun deleteSession(sessionId: Long) {
        viewModelScope.launch {
            sessionRepo.deleteSession(sessionId)
        }
    }
}

data class SessionGroupUiState(
    val total: Int = 0,
    val selesai: Int = 0,
    val proses: Int = 0,
    val tertunda: Int = 0
)

enum class DateFilter { TODAY, THIS_WEEK, THIS_MONTH, ALL }

// ─────────────────────────────────────────────────────────────────────────────
// CREATE SESSION VIEW MODEL
// Screen: Jadwal Baru (Image 3) – form buat sesi
// ─────────────────────────────────────────────────────────────────────────────

@HiltViewModel
class CreateSessionViewModel @Inject constructor(
    private val sessionRepo: InspectionSessionRepository
) : ViewModel() {

    // Form fields
    val title = MutableStateFlow("")
    val locationName = MutableStateFlow("")
    val inspectorName = MutableStateFlow("")
    val scheduledDate = MutableStateFlow(System.currentTimeMillis())
    val notes = MutableStateFlow("")

    // Weather auto-fetch saat lokasi GPS dipilih
    private val _weatherPreview = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weatherPreview: StateFlow<WeatherUiState> = _weatherPreview.asStateFlow()

    private val _createResult = MutableStateFlow<CreateSessionResult>(CreateSessionResult.Idle)
    val createResult: StateFlow<CreateSessionResult> = _createResult.asStateFlow()

    val isFormValid: StateFlow<Boolean> = combine(title, locationName, inspectorName) { t, l, i ->
        t.isNotBlank() && l.isNotBlank() && i.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun onLocationSelected(lat: Double, lon: Double, cityName: String) {
        locationName.value = cityName
        // Auto-fetch cuaca untuk preview
        viewModelScope.launch {
            _weatherPreview.value = WeatherUiState.Loading
            val result = sessionRepo.fetchWeatherByCity(cityName)
            result.fold(
                onSuccess = { _weatherPreview.value = WeatherUiState.Success(it) },
                onFailure = { _weatherPreview.value = WeatherUiState.Error("Tidak dapat memuat cuaca") }
            )
        }
    }

    fun createSession(inspectorId: String) {
        if (!isFormValid.value) return
        viewModelScope.launch {
            _createResult.value = CreateSessionResult.Loading
            try {
                val dateStr = SimpleDateFormat("yyyyMMdd-HHmm", Locale.getDefault())
                    .format(Date())
                val code = "INS-$dateStr"

                val sessionId = sessionRepo.createSession(
                    InspectionSessionEntity(
                        sessionCode   = code,
                        title         = title.value.trim(),
                        locationName  = locationName.value.trim(),
                        inspectorName = inspectorName.value.trim(),
                        inspectorId   = inspectorId,
                        scheduledDate = scheduledDate.value,
                        notes         = notes.value.trim(),
                        status        = SessionStatus.DRAFT
                    )
                )

                // Attach weather jika ada koordinat
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
        _createResult.value = CreateSessionResult.Idle
    }
}

sealed class CreateSessionResult {
    object Idle : CreateSessionResult()
    object Loading : CreateSessionResult()
    data class Success(val sessionId: Long) : CreateSessionResult()
    data class Error(val message: String) : CreateSessionResult()
}

// ─────────────────────────────────────────────────────────────────────────────
// SESSION DETAIL VIEW MODEL
// Screen: Detail Sesi & Ringkasan Temuan
// ─────────────────────────────────────────────────────────────────────────────

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

    fun startSession() {
        viewModelScope.launch { sessionRepo.startSession(sessionId) }
    }

    fun completeSession() {
        viewModelScope.launch { sessionRepo.completeSession(sessionId) }
    }

    fun addFinding(finding: InspectionFindingEntity) {
        viewModelScope.launch { findingRepo.addFinding(finding) }
    }

    fun markFindingResult(findingId: Long, result: FindingResult) {
        viewModelScope.launch { findingRepo.markFindingResult(findingId, result, sessionId) }
    }
}
