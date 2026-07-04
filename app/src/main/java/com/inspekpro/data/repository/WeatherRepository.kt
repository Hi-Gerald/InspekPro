package com.inspekpro.data.repository

import com.google.gson.Gson
import com.inspekpro.data.remote.model.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

interface WeatherRepository {
    suspend fun getWeatherByCoords(lat: Double, lon: Double): Result<WeatherInfo>
}

// Model JSON BMKG Baru
data class BmkgResponse(val data: List<BmkgData>?)
data class BmkgData(val lokasi: BmkgLokasi?, val cuaca: List<List<BmkgCuaca>>?)
data class BmkgLokasi(val adm4: String?, val provinsi: String?, val kotkab: String?, val kecamatan: String?, val desa: String?)
data class BmkgCuaca(
    val datetime: String?,
    val t: Double?, // Suhu
    val weather_desc: String?,
    val weather: Int?, // Kode Cuaca
    val ws: Double?, // Kecepatan Angin (km/h)
    val hu: Int?, // Kelembapan
    val image: String? // URL SVG
)

class BmkgWeatherRepositoryImpl(
    private val client: OkHttpClient = OkHttpClient()
) : WeatherRepository {

    private val gson = Gson()

    override suspend fun getWeatherByCoords(lat: Double, lon: Double): Result<WeatherInfo> =
        withContext(Dispatchers.IO) {
            try {
                val region = getBmkgAdm4(lat, lon)
                val url = "https://api.bmkg.go.id/publik/prakiraan-cuaca?adm4=${region.adm4}"
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                
                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception("HTTP Error: ${response.code}"))
                }
                
                val jsonString = response.body?.string()
                    ?: return@withContext Result.failure(Exception("Empty body"))
                    
                val parsed = gson.fromJson(jsonString, BmkgResponse::class.java)
                
                val weatherData = parsed.data?.firstOrNull() 
                    ?: return@withContext Result.failure(Exception("Data array kosong"))
                    
                // Cari data cuaca terdekat dengan waktu saat ini
                val closestCuaca = findClosestWeather(weatherData.cuaca)
                    ?: return@withContext Result.failure(Exception("Tidak ada data cuaca yang valid"))
                    
                val displayName = "${weatherData.lokasi?.desa ?: region.displayName}, ${weatherData.lokasi?.kotkab ?: ""}"
                
                Result.success(mapToWeatherInfo(closestCuaca, displayName))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    private data class BmkgRegion(val adm4: String, val displayName: String)

    private fun getBmkgAdm4(lat: Double, lon: Double): BmkgRegion {
        // Fallback untuk 0.0 (misalnya simulator) -> Kemayoran, Jakarta Pusat
        if (lat == 0.0 && lon == 0.0) return BmkgRegion("31.71.03.1001", "Kemayoran")
        
        return when {
            // Jakarta Raya (mapping ke Gambir / Kemayoran)
            lat in -6.40..-6.07 && lon in 106.65..107.00 -> BmkgRegion("31.71.03.1001", "Kemayoran")
            // Jawa Barat (Bandung - Babakan Ciamis)
            lat in -7.85..-5.90 && lon in 105.10..108.80 -> BmkgRegion("32.73.06.1002", "Sumur Bandung")
            // Jawa Tengah (Semarang)
            lat in -8.20..-6.40 && lon in 108.40..111.30 -> BmkgRegion("33.74.08.1004", "Semarang Tengah")
            // DIY (Yogyakarta)
            lat in -8.15..-7.55 && lon in 110.00..110.80 -> BmkgRegion("34.71.14.1003", "Gedongtengen")
            // Jawa Timur (Surabaya)
            lat in -8.80..-6.85 && lon in 111.00..115.00 -> BmkgRegion("35.78.08.1002", "Genteng")
            // Banten (Serang)
            lat in -7.00..-5.80 && lon in 105.10..106.65 -> BmkgRegion("36.73.03.1001", "Serang")
            // Bali (Denpasar)
            lat in -8.85..-8.05 && lon in 114.45..115.75 -> BmkgRegion("51.71.03.2006", "Denpasar Barat")
            // Sumatera Utara (Medan)
            lat in 1.00..4.20 && lon in 97.00..100.50 -> BmkgRegion("12.71.09.1002", "Medan Petisah")
            // Sumatera Selatan (Palembang)
            lat in -4.80..-1.40 && lon in 102.00..106.00 -> BmkgRegion("16.71.06.1002", "Ilir Barat I")
            // Kalimantan Timur (Samarinda)
            lat in -3.40..3.00 && lon in 114.50..119.00 -> BmkgRegion("64.72.03.1006", "Samarinda Ulu")
            // Sulawesi Selatan (Makassar)
            lat in -7.00..-1.00 && lon in 119.00..121.00 -> BmkgRegion("73.71.04.1005", "Ujung Pandang")
            // Default ke Jakarta jika di luar jangkauan (atau seluruh Indonesia belum dipetakan)
            else -> BmkgRegion("31.71.03.1001", "Kemayoran")
        }
    }

    private fun findClosestWeather(cuacaList: List<List<BmkgCuaca>>?): BmkgCuaca? {
        if (cuacaList.isNullOrEmpty()) return null
        
        val currentTime = System.currentTimeMillis()
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        format.timeZone = TimeZone.getTimeZone("UTC")
        
        var closest: BmkgCuaca? = null
        var minDiff = Long.MAX_VALUE
        
        val allCuaca = cuacaList.flatten()
        
        for (cuaca in allCuaca) {
            try {
                val datetime = cuaca.datetime ?: continue
                val time = format.parse(datetime)?.time ?: continue
                val diff = abs(time - currentTime)
                
                if (diff < minDiff) {
                    minDiff = diff
                    closest = cuaca
                }
            } catch (e: Exception) {
                // Ignore parse errors for individual items
            }
        }
        
        return closest
    }

    private fun mapToWeatherInfo(cuaca: BmkgCuaca, displayName: String): WeatherInfo {
        val weatherCode = cuaca.weather ?: -1
        val conditionDesc = cuaca.weather_desc ?: mapWeatherCodeToDesc(weatherCode)
        val iconCode = mapWeatherCodeToIcon(weatherCode)
        val isGood = weatherCode in listOf(0, 1, 2, 3, 4)
        val advice = when {
            weatherCode in listOf(60, 61, 63, 80) -> "Hujan, siapkan pelindung air"
            weatherCode in listOf(95, 97) -> "Petir! Tunda inspeksi luar"
            isGood -> "Kondisi baik untuk inspeksi lapangan"
            else -> "Pantau kondisi cuaca"
        }

        return WeatherInfo(
            cityName = displayName, 
            conditionMain = conditionDesc, 
            conditionDesc = conditionDesc,
            iconCode = iconCode, 
            iconUrl = cuaca.image ?: "", 
            tempCelsius = cuaca.t ?: 0.0, 
            feelsLikeCelsius = cuaca.t ?: 0.0,
            humidity = cuaca.hu ?: 0, 
            windSpeedMs = (cuaca.ws ?: 0.0) / 3.6, // km/h to m/s
            windSpeedKmh = cuaca.ws ?: 0.0, 
            pressureHpa = 1000,
            cloudCoverPercent = 0, 
            visibilityKm = 10.0, 
            isGoodForInspection = isGood, 
            inspectionAdvice = advice
        )
    }

    private fun mapWeatherCodeToDesc(code: Int): String = when (code) {
        0 -> "Cerah"; 1, 2 -> "Cerah Berawan"; 3 -> "Berawan"; 4 -> "Tebal Berawan"
        5 -> "Udara Kabur"; 10 -> "Asap"; 45 -> "Kabut"; 60 -> "Hujan Ringan"
        61 -> "Hujan Sedang"; 63 -> "Hujan Lebat"; 80 -> "Hujan Lokal"; 95, 97 -> "Hujan Petir"
        else -> "Cerah Berawan"
    }

    private fun mapWeatherCodeToIcon(code: Int): String = when (code) {
        0, 1, 2 -> "clear"; 3, 4, 5, 10, 45 -> "clouds"
        60, 61, 63, 80 -> "rain"; 95, 97 -> "thunderstorm"; else -> "clouds"
    }
}
