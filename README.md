# InspekPro — Backend Android (Room + Retrofit)

Dokumentasi lengkap layer data untuk aplikasi InspekPro.



## Struktur File

```
app/src/main/java/com/inspekpro/
├── data/
│   ├── local/
│   │   ├── entity/
│   │   │   ├── InspectionSessionEntity.kt   ← Sesi inspeksi
│   │   │   ├── InspectionFindingEntity.kt   ← Temuan per item
│   │   │   ├── ChecklistItemEntity.kt       ← Template & item checklist
│   │   │   └── PhotoAndSummaryEntity.kt     ← Foto temuan & ringkasan
│   │   ├── dao/
│   │   │   ├── InspectionSessionDao.kt      ← Query sesi
│   │   │   ├── InspectionFindingDao.kt      ← Query temuan + agregat
│   │   │   ├── ChecklistDao.kt              ← Query template checklist
│   │   │   └── PhotoAndSummaryDao.kt        ← Query foto & ringkasan
│   │   └── database/
│   │       └── InspekProDatabase.kt         ← RoomDatabase + TypeConverters
│   ├── remote/
│   │   ├── api/
│   │   │   └── WeatherApiService.kt         ← Retrofit service + OkHttp
│   │   └── model/
│   │       └── WeatherModels.kt             ← Response model + domain model
│   └── repository/
│       ├── InspectionSessionRepository.kt   ← Business logic sesi + cuaca
│       └── FindingRepository.kt             ← Business logic temuan + foto
├── ui/viewmodel/
│   └── ViewModels.kt                        ← Dashboard, List, Create, Detail VM
└── di/
    └── AppModule.kt                         ← Hilt DI providers
```

---

## Room Database — Entity Diagram

```
checklist_templates ──┐
                       │ (1:N)
checklist_items ───────┤
                       │ (N:1, nullable FK)
inspection_sessions ──── inspection_findings ──── finding_photos
        │                       │
        │ (1:1)                 │ (aggregated)
session_summaries ◄─────────────┘
```

### Entities & Kolom Penting

| Entity | Kolom Utama | Relasi |
|--------|-------------|--------|
| `inspection_sessions` | session_code, title, location, status, weather_*, scheduled_date | parent |
| `inspection_findings` | finding_code, category, severity, result, status | FK → session |
| `checklist_templates` | name, inspection_type, version | parent |
| `checklist_items` | item_code, question, category, is_mandatory | FK → template |
| `finding_photos` | local_path, remote_url, is_uploaded | FK → finding |
| `session_summaries` | compliance_score, overall_grade, *_count | FK → session |




## OpenWeatherMap API

### Setup
1. Daftar di [openweathermap.org](https://openweathermap.org) → Free tier cukup (60 call/min)
2. Tambahkan API key di `local.properties`:
   ```
   OPENWEATHER_API_KEY=your_key_here
   ```
3. Endpoint yang dipakai: `GET /data/2.5/weather`

### Endpoints
```kotlin
// By coordinates (GPS)
weatherApi.getWeatherByCoordinates(lat = -6.2, lon = 106.8)

// By city name (manual input)
weatherApi.getWeatherByCity("Jakarta")
```

### Auto-attach ke Sesi
Saat sesi baru dibuat dengan lokasi GPS, cuaca otomatis di-fetch dan disimpan ke `inspection_sessions.weather_*`.

---

## Arsitektur

```
Activity/Fragment
      ↓  observe
  ViewModel (StateFlow / LiveData)
      ↓  suspend fun / Flow
  Repository
      ↓              ↓
  Room DAO      Retrofit API
      ↓
  SQLite (inspekpro.db)
```

Pattern: **MVVM + Repository + Clean Architecture**



## 🔗 Dependency (build.gradle.kts)

| Library | Versi | Kegunaan |
|---------|-------|----------|
| Room | 2.6.1 | SQLite ORM |
| Retrofit | 2.9.0 | HTTP client |
| OkHttp Logging | 4.12.0 | Debug network |
| Hilt | 2.50 | Dependency injection |
| Coroutines | 1.7.3 | Async + Flow |
| Lifecycle/ViewModel | 2.7.0 | UI state |
| Navigation | 2.7.7 | Fragment navigation |
| Glide | 4.16.0 | Load icon cuaca |
| Play Services Location | 21.2 | GPS koordinat |

---

## Cara Pakai di Activity/Fragment

```kotlin
// Dashboard: observe cuaca & stats
@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private val vm: DashboardViewModel by viewModels()

    override fun onViewCreated(...) {
        // Minta lokasi GPS lalu load cuaca
        vm.loadWeatherByCoords(lat, lon)

        viewLifecycleOwner.lifecycleScope.launch {
            vm.weather.collect { state ->
                when (state) {
                    is WeatherUiState.Success -> {
                        binding.tvTemp.text = "${state.data.tempCelsius}°C"
                        binding.tvAdvice.text = state.data.inspectionAdvice
                    }
                    is WeatherUiState.Error -> showError(state.message)
                    WeatherUiState.Loading  -> showLoading()
                }
            }
        }
    }
}

// Buat sesi baru (Jadwal Baru screen)
@AndroidEntryPoint
class CreateSessionFragment : Fragment() {
    private val vm: CreateSessionViewModel by viewModels()

    fun onSelesaiClicked() {
        vm.createSession(inspectorId = currentUser.id)
        viewLifecycleOwner.lifecycleScope.launch {
            vm.createResult.collect { result ->
                if (result is CreateSessionResult.Success) {
                    navigate to SessionDetailFragment(result.sessionId)
                }
            }
        }
    }
}
```



## Dashboard Stats Query

```kotlin
// Langsung dari DB (sesuai tampilan Image 1: 248, 189, 12, 203)
val stats = summaryDao.getDashboardStats()
// → DashboardStats(totalSessions=248, completedSessions=189, totalFindings=..., totalCritical=12)
```



## Alur Lengkap "Buat Sesi Inspeksi"

```
1. User tap (+) → CreateSessionFragment
2. Isi Judul/Nama Mesin + Lokasi
3. GPS auto-detect → fetchWeatherByCoords() → tampil preview cuaca
4. Tap "Selesai" → createSession() → insert ke Room
5. Navigate ke SessionDetailFragment
6. Tap "Mulai Inspeksi" → startSession() → status = IN_PROGRESS
7. Isi findings satu per satu → addFinding() → refreshSummary()
8. Tap "Selesai Inspeksi" → completeSession() → summary dihitung
9. Lihat Ringkasan Temuan: compliance_score, grade, critical_count, dll
```
