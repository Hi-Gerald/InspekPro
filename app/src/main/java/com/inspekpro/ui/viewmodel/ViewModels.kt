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
    private val findingRepo: FindingRepository,
    private val firestoreSyncRepo: FirestoreSyncRepository
) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weather: StateFlow<WeatherUiState> = _weather.asStateFlow()

    // Guard: mencegah pemanggilan ganda (rotasi layar, GPS callback berulang, dll.)
    private var isLoadingWeather = false

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
        // Abaikan jika request sebelumnya masih berjalan → cegah loop
        if (isLoadingWeather) return

        viewModelScope.launch {
            isLoadingWeather = true
            _weather.value = WeatherUiState.Loading
            try {
                val result = sessionRepo.fetchWeatherByCoordinates(lat, lon)
                result.fold(
                    onSuccess = { _weather.value = WeatherUiState.Success(it) },
                    onFailure = { _weather.value = WeatherUiState.Error(it.message ?: "Gagal memuat cuaca") }
                )
            } finally {
                // Selalu reset flag, bahkan kalau ada exception tak terduga
                isLoadingWeather = false
            }
        }
    }

    /**
     * Overload untuk pemanggilan berdasarkan nama kota (mis. dari DashboardFragment
     * ketika GPS tidak tersedia).
     */
    fun loadWeatherByCity(cityName: String) {
        if (isLoadingWeather) return

        viewModelScope.launch {
            isLoadingWeather = true
            _weather.value = WeatherUiState.Loading
            try {
                val result = sessionRepo.fetchWeatherByCity(cityName)
                result.fold(
                    onSuccess = { _weather.value = WeatherUiState.Success(it) },
                    onFailure = { _weather.value = WeatherUiState.Error(it.message ?: "Gagal memuat cuaca") }
                )
            } finally {
                isLoadingWeather = false
            }
        }
    }

    private val _syncStatus = MutableSharedFlow<Result<Int>>()
    val syncStatus: SharedFlow<Result<Int>> = _syncStatus.asSharedFlow()

    fun syncNow() {
        viewModelScope.launch {
            val result = firestoreSyncRepo.syncUnsyncedSessions()
            _syncStatus.emit(result)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Bagian Anom: Create Session ViewModel
// Fitur: Validasi input jadwal, Integrasi AlarmManager, & Sync Firestore
// Untuk: Menangani logika bisnis pembuatan jadwal inspeksi baru dan sinkronisasi data.
// ─────────────────────────────────────────────────────────────────────────────

@HiltViewModel
class CreateSessionViewModel @Inject constructor(
    private val sessionRepo: InspectionSessionRepository,
    private val alarmScheduler: AlarmScheduler,
    private val firestoreSyncRepo: FirestoreSyncRepository
) : ViewModel() {

    val title = MutableStateFlow("")
    val locationName = MutableStateFlow("")
    val inspectorName = MutableStateFlow("")
    val scheduledDate = MutableStateFlow(System.currentTimeMillis())
    val notes = MutableStateFlow("")
    val videoPath = MutableStateFlow<String?>(null)

    // TAMBAHAN ANOM: Field untuk menyimpan koordinat GPS temporer
    private var lastLat: Double? = null
    private var lastLon: Double? = null

    private val _createResult = MutableStateFlow<CreateSessionResult>(CreateSessionResult.Idle)
    val createResult: StateFlow<CreateSessionResult> = _createResult.asStateFlow()

    val isFormValid: StateFlow<Boolean> = combine(title, locationName, inspectorName) { t, l, i ->
        t.isNotBlank() && l.isNotBlank() && i.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    // TAMBAHAN ANOM: Fungsi baru dipanggil dari Fragment setelah mendapatkan GPS
    fun setGpsCoordinates(lat: Double, lon: Double) {
        lastLat = lat
        lastLon = lon
    }

    fun createSession(inspectorId: String, totalItems: Int = 0, passedItems: Int = 0) {
        // Bagian Anom: Cek langsung dari nilai field (menghindari delay stateIn)
        if (title.value.isBlank() || locationName.value.isBlank() || inspectorName.value.isBlank()) {
            _createResult.value = CreateSessionResult.Error("Lengkapi nama objek, lokasi, dan inspektor")
            return
        }
        
        // Validasi: Waktu inspeksi tidak boleh di masa lalu
        if (scheduledDate.value < System.currentTimeMillis()) {
            _createResult.value = CreateSessionResult.Error("Waktu inspeksi harus di masa depan")
            return
        }

        // Mencegah trigger ganda/dobel submit saat loading berjalan
        if (_createResult.value is CreateSessionResult.Loading) return

        viewModelScope.launch {
            _createResult.value = CreateSessionResult.Loading
            try {
                val dateStr = SimpleDateFormat("yyyyMMdd-HHmm", Locale.getDefault()).format(Date())
                val code = "INS-$dateStr"

                val newSession = InspectionSessionEntity(
                    sessionCode   = code,
                    title         = title.value.trim(),
                    locationName  = locationName.value.trim(),
                    inspectorName = inspectorName.value.trim(),
                    inspectorId   = inspectorId,
                    scheduledDate = scheduledDate.value,
                    notes         = notes.value.trim(),
                    reportVideoPath = videoPath.value,
                    totalItems    = totalItems,
                    passedItems   = passedItems,
                    status        = SessionStatus.DRAFT
                )

                val sessionId = sessionRepo.createSession(newSession)
                
                // Jadwalkan Pengingat (AlarmManager)
                alarmScheduler.schedule(newSession.copy(sessionId = sessionId))

                // Data lokal & alarm sudah aman → langsung sukses (UI tidak nge-block)
                _createResult.value = CreateSessionResult.Success(sessionId)

                // TAMBAHAN ANOM: Proses fetch cuaca di background agar tidak mengunci UI utama
                val lat = lastLat
                val lon = lastLon
                launch {
                    try {
                        if (lat != null && lon != null) {
                            // GPS tersedia → pakai koordinat (paling akurat)
                            sessionRepo.fetchAndAttachWeather(sessionId, lat, lon)
                        } else if (locationName.value.isNotBlank()) {
                            // Fallback → pakai nama lokasi yang diketik user
                            sessionRepo.fetchWeatherByCity(locationName.value.trim())
                                .onSuccess { info -> 
                                    sessionRepo.attachWeatherToSession(sessionId, info) 
                                }
                        }
                    } catch (weatherError: Exception) {
                        weatherError.printStackTrace() // Gagal cuaca tidak membatalkan session success
                    }
                }

                // Sinkron ke Cloud di background (tidak memblokir user)
                launch {
                    try {
                        firestoreSyncRepo.syncUnsyncedSessions()
                    } catch (syncError: Exception) {
                        syncError.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                _createResult.value = CreateSessionResult.Error(e.message ?: "Gagal membuat sesi")
            }
        }
    }

    fun resetForm() {
        title.value = ""
        locationName.value = ""
        inspectorName.value = ""
        notes.value = ""
        videoPath.value = null
        lastLat = null // Reset koordinat GPS
        lastLon = null // Reset koordinat GPS
        scheduledDate.value = System.currentTimeMillis()
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
    private val _filter = MutableStateFlow(DateFilter.TODAY)
    val filter: StateFlow<DateFilter> = _filter.asStateFlow()
    val allSessions = sessionRepo.getAllSessions()
    val sessionsByStatus: StateFlow<SessionGroupUiState> = allSessions.map { sessions ->
        SessionGroupUiState(
            total = sessions.size,
            selesai = sessions.count { it.status == SessionStatus.COMPLETED },
            proses = sessions.count { it.status == SessionStatus.IN_PROGRESS },
            tertunda = sessions.count { it.status == SessionStatus.DRAFT }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SessionGroupUiState())
    val activeSessions: Flow<List<InspectionSessionEntity>> = allSessions.map { list ->
        list.filter { it.status in listOf(SessionStatus.IN_PROGRESS, SessionStatus.DRAFT) }
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