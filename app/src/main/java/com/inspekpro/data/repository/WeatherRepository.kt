package com.inspekpro.data.repository

import com.inspekpro.data.remote.model.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

interface WeatherRepository {
    suspend fun getWeatherByCoords(lat: Double, lon: Double): Result<WeatherInfo>
}

class BmkgWeatherRepositoryImpl(
    private val client: OkHttpClient = OkHttpClient()
) : WeatherRepository {

    override suspend fun getWeatherByCoords(lat: Double, lon: Double): Result<WeatherInfo> =
        withContext(Dispatchers.IO) {
            try {
                val region = getBmkgRegion(lat, lon)
                val url = "https://data.bmkg.go.id/DataMKG/MEWS/DigitalForecast/DigitalForecast-${region.fileName}.xml"
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception("HTTP Error: ${response.code}"))
                }
                val xmlString = response.body?.string()
                    ?: return@withContext Result.failure(Exception("Empty body"))
                val weatherInfo = parseBmkgXml(xmlString, region.areaKeyword, region.displayName)
                if (weatherInfo != null) Result.success(weatherInfo)
                else Result.failure(Exception("Data wilayah tidak ditemukan di XML BMKG"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    private data class BmkgRegion(val fileName: String, val areaKeyword: String, val displayName: String)

    private fun getBmkgRegion(lat: Double, lon: Double): BmkgRegion {
        if (lat == 0.0 && lon == 0.0) return BmkgRegion("DKIJakarta", "Jakarta", "DKI Jakarta")
        return when {
            lat in -6.40..-6.07 && lon in 106.65..107.00 -> BmkgRegion("DKIJakarta", "Jakarta", "DKI Jakarta")
            lat in -7.85..-5.90 && lon in 105.10..108.80 -> BmkgRegion("JawaBarat", "Bandung", "Jawa Barat")
            lat in -8.20..-6.40 && lon in 108.40..111.30 -> BmkgRegion("JawaTengah", "Semarang", "Jawa Tengah")
            lat in -8.15..-7.55 && lon in 110.00..110.80 -> BmkgRegion("DIYogyakarta", "Yogyakarta", "DI Yogyakarta")
            lat in -8.80..-6.85 && lon in 111.00..115.00 -> BmkgRegion("JawaTimur", "Surabaya", "Jawa Timur")
            lat in -7.00..-5.80 && lon in 105.10..106.65 -> BmkgRegion("Banten", "Serang", "Banten")
            lat in -8.85..-8.05 && lon in 114.45..115.75 -> BmkgRegion("Bali", "Denpasar", "Bali")
            lat in 1.00..4.20 && lon in 97.00..100.50 -> BmkgRegion("SumateraUtara", "Medan", "Sumatera Utara")
            lat in -4.80..-1.40 && lon in 102.00..106.00 -> BmkgRegion("SumateraSelatan", "Palembang", "Sumatera Selatan")
            lat in -3.40..3.00 && lon in 114.50..119.00 -> BmkgRegion("KalimantanTimur", "Samarinda", "Kalimantan Timur")
            lat in -7.00..-1.00 && lon in 119.00..121.00 -> BmkgRegion("SulawesiSelatan", "Makassar", "Sulawesi Selatan")
            else -> BmkgRegion("DKIJakarta", "Jakarta", "DKI Jakarta")
        }
    }

    private fun parseBmkgXml(xml: String, areaKeyword: String, displayName: String): WeatherInfo? {
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(StringReader(xml))
        var eventType = parser.eventType
        var isTargetArea = false
        var currentParamId = ""
        var currentTemp = 0.0
        var currentHumidity = 0
        var weatherCode = -1
        var insideTimerange = false
        var gotTemp = false
        var gotHumidity = false
        var gotWeather = false

        while (eventType != XmlPullParser.END_DOCUMENT) {
            val tagName = parser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (tagName == "area") {
                        val description = parser.getAttributeValue(null, "description")
                        if (description?.contains(areaKeyword, ignoreCase = true) == true) isTargetArea = true
                    }
                    if (isTargetArea && tagName == "parameter") currentParamId = parser.getAttributeValue(null, "id") ?: ""
                    if (isTargetArea && tagName == "timerange") {
                        if ((currentParamId == "t" && !gotTemp) || (currentParamId == "hu" && !gotHumidity) || (currentParamId == "weather" && !gotWeather))
                            insideTimerange = true
                    }
                    if (insideTimerange && tagName == "value") {
                        parser.next()
                        val text = parser.text
                        when (currentParamId) {
                            "t" -> { currentTemp = text.toDoubleOrNull() ?: 0.0; gotTemp = true }
                            "hu" -> { currentHumidity = text.toIntOrNull() ?: 0; gotHumidity = true }
                            "weather" -> { weatherCode = text.toIntOrNull() ?: -1; gotWeather = true }
                        }
                        insideTimerange = false
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (tagName == "area" && isTargetArea) {
                        if (gotTemp && gotWeather) break
                        isTargetArea = false
                    }
                }
            }
            eventType = parser.next()
        }

        if (!gotTemp || !gotWeather) return null

        val conditionDesc = mapWeatherCodeToDesc(weatherCode)
        val iconCode = mapWeatherCodeToIcon(weatherCode)
        val isGood = weatherCode in listOf(0, 1, 2, 3, 4)
        val advice = when {
            weatherCode in listOf(60, 61, 63, 80) -> "Hujan, siapkan pelindung air"
            weatherCode in listOf(95, 97) -> "Petir! Tunda inspeksi luar"
            isGood -> "Kondisi baik untuk inspeksi lapangan"
            else -> "Pantau kondisi cuaca"
        }

        return WeatherInfo(
            cityName = displayName, conditionMain = conditionDesc, conditionDesc = conditionDesc,
            iconCode = iconCode, iconUrl = "", tempCelsius = currentTemp, feelsLikeCelsius = currentTemp,
            humidity = currentHumidity, windSpeedMs = 0.0, windSpeedKmh = 0.0, pressureHpa = 1000,
            cloudCoverPercent = 0, visibilityKm = 10.0, isGoodForInspection = isGood, inspectionAdvice = advice
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
