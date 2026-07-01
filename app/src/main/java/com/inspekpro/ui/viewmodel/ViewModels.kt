package com.inspekpro.ui.viewmodel

import androidx.lifecycle.*
import com.inspekpro.data.local.entity.*
import com.inspekpro.data.local.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.inspekpro.data.remote.model.WeatherInfo
import com.inspekpro.data.repository.FindingRepository
import com.inspekpro.data.repository.InspectionSessionRepository
import com.inspekpro.data.repository.FirestoreSyncRepository
import com.inspekpro.data.repository.AuthRepository
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

    val activeSessions = sessionRepo.getAllSessions().map { list ->
        list.filter { it.status == SessionStatus.IN_PROGRESS }
            .sortedByDescending { it.scheduledDate }
            .take(3)
    }
    
    // Recent findings: take(3)
    val recentFindings = findingRepo.getRecentFindings(10).map { list ->
        list.take(3)
    }

    // Dashboard stats observed from Room
    val dashboardStats = sessionRepo.getAllSessions().mapLatest {
        sessionRepo.getDashboardStats()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

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
}

// ─────────────────────────────────────────────────────────────────────────────
// Bagian Billy: Create Session ViewModel
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
    val scheduledDate = MutableStateFlow(0L)
    val notes = MutableStateFlow("")
    val videoPath = MutableStateFlow<String?>(null)
    val hasFindings = MutableStateFlow(false)

    private val _createResult = MutableStateFlow<CreateSessionResult>(CreateSessionResult.Idle)
    val createResult: StateFlow<CreateSessionResult> = _createResult.asStateFlow()

    private val _existingSession = MutableStateFlow<InspectionSessionEntity?>(null)
    val existingSession: StateFlow<InspectionSessionEntity?> = _existingSession.asStateFlow()

    val isFormValid: StateFlow<Boolean> = combine(title, locationName, inspectorName) { t, l, i ->
        t.isNotBlank() && l.isNotBlank() && i.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun loadSession(sessionId: Long) {
        if (sessionId == -1L) {
            resetForm()
            return
        }
        viewModelScope.launch {
            sessionRepo.getSessionById(sessionId).collectLatest { session ->
                session?.let {
                    _existingSession.value = it
                    title.value = it.title
                    locationName.value = it.locationName
                    inspectorName.value = it.inspectorName
                    scheduledDate.value = it.scheduledDate
                    notes.value = it.notes ?: ""
                    videoPath.value = it.reportVideoPath
                }
            }
        }
    }

    fun createSession(
        inspectorId: String, 
        status: SessionStatus = SessionStatus.DRAFT,
        manualTitle: String? = null,
        manualLocation: String? = null,
        manualInspector: String? = null,
        manualConclusion: String? = null,
        manualPhotos: List<String> = emptyList(),
        manualVideo: String? = null
    ) {
        // Bagian Billy: Cek langsung dari nilai field (menghindari delay stateIn)
        val finalTitle = manualTitle ?: title.value
        val finalLocation = manualLocation ?: locationName.value
        val finalInspector = manualInspector ?: inspectorName.value

        if (finalTitle.isBlank() || finalLocation.isBlank() || finalInspector.isBlank()) {
            _createResult.value = CreateSessionResult.Error("Lengkapi nama objek, lokasi, dan inspektor")
            return
        }
        
        // Validasi: Waktu inspeksi tidak boleh kosong atau di masa lalu (only for new sessions)
        if (_existingSession.value == null) {
            if (scheduledDate.value == 0L) {
                _createResult.value = CreateSessionResult.Error("Pilih tanggal dan waktu inspeksi")
                return
            }
            if (scheduledDate.value < System.currentTimeMillis() - 60000) { 
                _createResult.value = CreateSessionResult.Error("Waktu inspeksi harus di masa depan")
                return
            }
        }

        viewModelScope.launch {
            _createResult.value = CreateSessionResult.Loading
            try {
                val currentExisting = _existingSession.value
                val sessionId: Long

                if (currentExisting != null) {
                    // Update existing
                    val updatedSession = currentExisting.copy(
                        title         = finalTitle.trim(),
                        locationName  = finalLocation.trim(),
                        inspectorName = finalInspector.trim(),
                        scheduledDate = scheduledDate.value,
                        notes         = manualConclusion ?: notes.value.trim(),
                        reportVideoPath = manualVideo ?: videoPath.value,
                        status        = status,
                        updatedAt     = System.currentTimeMillis()
                    )
                    sessionRepo.updateSession(updatedSession)
                    sessionId = updatedSession.sessionId
                    
                    // Reschedule alarm
                    alarmScheduler.schedule(updatedSession)
                } else {
                    // Create new
                    val dateStr = SimpleDateFormat("yyyyMMdd-HHmm", Locale.getDefault()).format(Date())
                    val code = "INS-$dateStr"

                    val newSession = InspectionSessionEntity(
                        sessionCode   = code,
                        title         = finalTitle.trim(),
                        locationName  = finalLocation.trim(),
                        inspectorName = finalInspector.trim(),
                        inspectorId   = inspectorId,
                        scheduledDate = scheduledDate.value,
                        notes         = manualConclusion ?: notes.value.trim(),
                        reportVideoPath = manualVideo ?: videoPath.value,
                        totalItems    = 0,
                        passedItems   = 0,
                        status        = status
                    )
                    sessionId = sessionRepo.createSession(newSession)
                    alarmScheduler.schedule(newSession.copy(sessionId = sessionId))
                }

                _createResult.value = CreateSessionResult.Success(sessionId)

                launch {
                    firestoreSyncRepo.syncUnsyncedSessions()
                }
            } catch (e: Exception) {
                _createResult.value = CreateSessionResult.Error(e.message ?: "Gagal memproses sesi")
            }
        }
    }

    fun resetForm() {
        _existingSession.value = null
        title.value = ""
        locationName.value = ""
        inspectorName.value = ""
        notes.value = ""
        videoPath.value = null
        scheduledDate.value = 0L
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
    
    private val _selectedDateMillis = MutableStateFlow(System.currentTimeMillis())
    val selectedDateMillis: StateFlow<Long> = _selectedDateMillis.asStateFlow()

    val allSessions = sessionRepo.getAllSessions()

    val filteredSessions: StateFlow<List<InspectionSessionEntity>> = 
        combine(allSessions, _selectedDateMillis) { sessions, date ->
            val cal = Calendar.getInstance().apply { timeInMillis = date }
            sessions.filter { 
                val sessionCal = Calendar.getInstance().apply { timeInMillis = it.scheduledDate }
                sessionCal.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                sessionCal.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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
    fun setSelectedDate(millis: Long) { _selectedDateMillis.value = millis }
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

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val activeUser: StateFlow<UserEntity?> = authRepository.getActiveUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _updateResult = MutableStateFlow<ProfileUpdateResult>(ProfileUpdateResult.Idle)
    val updateResult: StateFlow<ProfileUpdateResult> = _updateResult.asStateFlow()

    fun updateProfile(fullName: String, companyName: String) {
        viewModelScope.launch {
            _updateResult.value = ProfileUpdateResult.Loading
            try {
                val currentUser = activeUser.value
                if (currentUser != null) {
                    val updatedUser = currentUser.copy(
                        fullName = fullName,
                        companyName = companyName
                    )
                    val result = authRepository.updateUser(updatedUser)
                    result.fold(
                        onSuccess = { _updateResult.value = ProfileUpdateResult.Success },
                        onFailure = { exception -> _updateResult.value = ProfileUpdateResult.Error(exception.message ?: "Gagal memperbarui profil") }
                    )
                } else {
                    _updateResult.value = ProfileUpdateResult.Error("Sesi pengguna tidak ditemukan")
                }
            } catch (e: Exception) {
                _updateResult.value = ProfileUpdateResult.Error(e.message ?: "Gagal memperbarui profil")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logoutUser()
        }
    }

    fun resetResult() {
        _updateResult.value = ProfileUpdateResult.Idle
    }
}

sealed class ProfileUpdateResult {
    object Idle : ProfileUpdateResult()
    object Loading : ProfileUpdateResult()
    object Success : ProfileUpdateResult()
    data class Error(val message: String) : ProfileUpdateResult()
}

// ─────────────────────────────────────────────────────────────────────────────
// FORGOT PASSWORD VIEW MODEL
// ─────────────────────────────────────────────────────────────────────────────

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    private val _resetResult = MutableStateFlow<ResetPasswordResult>(ResetPasswordResult.Idle)
    val resetResult: StateFlow<ResetPasswordResult> = _resetResult.asStateFlow()

    fun resetPassword(email: String, newPasswordHash: String) {
        viewModelScope.launch {
            _resetResult.value = ResetPasswordResult.Loading
            try {
                val user = userDao.getUserByEmail(email)
                if (user == null) {
                    _resetResult.value = ResetPasswordResult.Error("Email tidak ditemukan.")
                    return@launch
                }

                // Hash password
                val hashedPassword = hashPassword(newPasswordHash)
                val updatedUser = user.copy(passwordHash = hashedPassword)
                userDao.updateUser(updatedUser)
                
                _resetResult.value = ResetPasswordResult.Success
            } catch (e: Exception) {
                _resetResult.value = ResetPasswordResult.Error(e.message ?: "Gagal memperbarui password")
            }
        }
    }

    fun resetResult() {
        _resetResult.value = ResetPasswordResult.Idle
    }

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = java.security.MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}

sealed class ResetPasswordResult {
    object Idle : ResetPasswordResult()
    object Loading : ResetPasswordResult()
    object Success : ResetPasswordResult()
    data class Error(val message: String) : ResetPasswordResult()
}

