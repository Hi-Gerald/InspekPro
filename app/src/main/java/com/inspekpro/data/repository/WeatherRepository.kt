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
    suspend fun getCurrentWeather(): Result<WeatherInfo>
}

class BmkgWeatherRepositoryImpl(
    private val client: OkHttpClient = OkHttpClient()
) : WeatherRepository {

    override suspend fun getCurrentWeather(): Result<WeatherInfo> = withContext(Dispatchers.IO) {
        try {
            // BMKG URL for DKI Jakarta
            val url = "https://data.bmkg.go.id/DataMKG/MEWS/DigitalForecast/DigitalForecast-DKIJakarta.xml"
            val request = Request.Builder().url(url).build()
            
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("HTTP Error: ${response.code}"))
            }
            
            val xmlString = response.body?.string() ?: return@withContext Result.failure(Exception("Empty body"))
            
            val weatherInfo = parseBmkgXml(xmlString)
            if (weatherInfo != null) {
                Result.success(weatherInfo)
            } else {
                Result.failure(Exception("Data wilayah tidak ditemukan atau format tidak sesuai"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseBmkgXml(xml: String): WeatherInfo? {
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
                        if (description?.contains("Jakarta Selatan", ignoreCase = true) == true) {
                            isTargetArea = true
                        }
                    }
                    if (isTargetArea && tagName == "parameter") {
                        currentParamId = parser.getAttributeValue(null, "id") ?: ""
                    }
                    if (isTargetArea && tagName == "timerange") {
                        // kita hanya ambil timerange pertama yang merupakan forecast saat ini/terdekat
                        if ((currentParamId == "t" && !gotTemp) || 
                            (currentParamId == "hu" && !gotHumidity) || 
                            (currentParamId == "weather" && !gotWeather)) {
                            insideTimerange = true
                        }
                    }
                    if (insideTimerange && tagName == "value") {
                        parser.next()
                        val text = parser.text
                        when (currentParamId) {
                            "t" -> {
                                currentTemp = text.toDoubleOrNull() ?: 0.0
                                gotTemp = true
                            }
                            "hu" -> {
                                currentHumidity = text.toIntOrNull() ?: 0
                                gotHumidity = true
                            }
                            "weather" -> {
                                weatherCode = text.toIntOrNull() ?: -1
                                gotWeather = true
                            }
                        }
                        insideTimerange = false
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (tagName == "area" && isTargetArea) {
                        break
                    }
                }
            }
            eventType = parser.next()
        }

        if (gotTemp && gotWeather) {
            val conditionDesc = mapWeatherCodeToDesc(weatherCode)
            val iconCode = mapWeatherCodeToIcon(weatherCode)
            
            val isGood = weatherCode in listOf(0, 1, 2, 3, 4) // Cerah / Berawan
            val advice = when {
                weatherCode in listOf(60, 61, 63, 80) -> "⚠️ Hujan, siapkan pelindung air"
                weatherCode in listOf(95, 97) -> "🚨 Petir! Tunda inspeksi luar"
                isGood -> "✅ Kondisi baik untuk inspeksi lapangan"
                else -> "ℹ️ Pantau kondisi cuaca"
            }

            return WeatherInfo(
                cityName = "Jakarta Selatan (Kebayoran)",
                conditionMain = conditionDesc,
                conditionDesc = conditionDesc,
                iconCode = iconCode,
                iconUrl = "", 
                tempCelsius = currentTemp,
                feelsLikeCelsius = currentTemp,
                humidity = currentHumidity,
                windSpeedMs = 0.0,
                windSpeedKmh = 0.0,
                pressureHpa = 1000,
                cloudCoverPercent = 0,
                visibilityKm = 10.0,
                isGoodForInspection = isGood,
                inspectionAdvice = advice
            )
        }
        return null
    }

    private fun mapWeatherCodeToDesc(code: Int): String {
        return when (code) {
            0 -> "Cerah"
            1, 2 -> "Cerah Berawan"
            3 -> "Berawan"
            4 -> "Tebal Berawan"
            5 -> "Udara Kabur"
            10 -> "Asap"
            45 -> "Kabut"
            60 -> "Hujan Ringan"
            61 -> "Hujan Sedang"
            63 -> "Hujan Lebat"
            80 -> "Hujan Lokal"
            95, 97 -> "Hujan Petir"
            else -> "Cerah Berawan"
        }
    }

    private fun mapWeatherCodeToIcon(code: Int): String {
        return when (code) {
            0, 1, 2 -> "clear"
            3, 4, 5, 10, 45 -> "clouds"
            60, 61, 63, 80 -> "rain"
            95, 97 -> "thunderstorm"
            else -> "clouds"
        }
    }
}
