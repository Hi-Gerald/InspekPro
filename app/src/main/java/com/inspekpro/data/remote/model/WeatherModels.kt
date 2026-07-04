package com.inspekpro.data.remote.model

// ─── DOMAIN MODEL (dipakai di seluruh app, diisi dari BMKG via WeatherRepository) ───
// Catatan merge: kelas response OpenWeatherMap (WeatherResponse, MainWeather, dll.) dan
// WeatherInfo.fromResponse() dihapus karena sumber cuaca sudah disatukan ke BMKG, yang
// membangun WeatherInfo langsung dari hasil parsing XML (lihat BmkgWeatherRepositoryImpl).

data class WeatherInfo(
    val cityName: String,
    val conditionMain: String,       // mis. "Cerah Berawan"
    val conditionDesc: String,       // deskripsi yang ditampilkan ke user
    val iconCode: String,            // "clear" | "clouds" | "rain" | "thunderstorm"
    val iconUrl: String,             // dikosongkan untuk BMKG (tidak ada icon URL resmi)
    val tempCelsius: Double,
    val feelsLikeCelsius: Double,
    val humidity: Int,
    val windSpeedMs: Double,
    val windSpeedKmh: Double,
    val pressureHpa: Int,
    val cloudCoverPercent: Int,
    val visibilityKm: Double,
    val isGoodForInspection: Boolean,  // kondisi baik untuk inspeksi lapangan
    val inspectionAdvice: String
)
