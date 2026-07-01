package com.inspekpro.data.remote.model

import com.google.gson.annotations.SerializedName
import java.util.Locale

// ─── RESPONSE MODELS ──────────────────────────────────────────────────────────

data class WeatherResponse(
    @SerializedName("coord")      val coord: Coord,
    @SerializedName("weather")    val weather: List<WeatherDescription>,
    @SerializedName("main")       val main: MainWeather,
    @SerializedName("wind")       val wind: Wind,
    @SerializedName("clouds")     val clouds: Clouds,
    @SerializedName("rain")       val rain: Rain? = null,
    @SerializedName("visibility") val visibility: Int = 0,
    @SerializedName("dt")         val dt: Long,
    @SerializedName("sys")        val sys: Sys,
    @SerializedName("name")       val name: String,
    @SerializedName("cod")        val cod: Int
)

data class Coord(
    @SerializedName("lon") val lon: Double,
    @SerializedName("lat") val lat: Double
)

data class WeatherDescription(
    @SerializedName("id")          val id: Int,
    @SerializedName("main")        val main: String,       // "Rain", "Clear", etc.
    @SerializedName("description") val description: String, // "berawan sebagian"
    @SerializedName("icon")        val icon: String        // "10d"
)

data class MainWeather(
    @SerializedName("temp")       val temp: Double,        // Celsius (units=metric diminta di query)
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min")   val tempMin: Double,
    @SerializedName("temp_max")   val tempMax: Double,
    @SerializedName("pressure")   val pressure: Int,       // hPa
    @SerializedName("humidity")   val humidity: Int        // %
)

data class Wind(
    @SerializedName("speed") val speed: Double,  // m/s
    @SerializedName("deg")   val deg: Int,
    @SerializedName("gust")  val gust: Double? = null
)

data class Clouds(
    @SerializedName("all") val all: Int  // %
)

data class Rain(
    @SerializedName("1h") val oneHour: Double? = null,
    @SerializedName("3h") val threeHour: Double? = null
)

data class Sys(
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset")  val sunset: Long
)

// ─── DOMAIN MODEL (sudah diproses ke satuan metric) ───────────────────────────

data class WeatherInfo(
    val cityName: String,
    val conditionMain: String,       // "Rain"
    val conditionDesc: String,       // "berawan sebagian"
    val iconCode: String,            // "10d"
    val iconUrl: String,             // full URL ke icon
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
) {
    companion object {
        fun fromResponse(response: WeatherResponse): WeatherInfo {
            // units=metric sudah diminta di query → API mengembalikan Celsius langsung.
            // JANGAN kurangi 273.15 lagi; itu untuk respons tanpa units (Kelvin).
            val tempC = response.main.temp
            val feelsLikeC = response.main.feelsLike
            val windKmh = response.wind.speed * 3.6
            val visKm = response.visibility / 1000.0
            val desc = response.weather.firstOrNull()?.description ?: ""
            val icon = response.weather.firstOrNull()?.icon ?: ""
            val condMain = response.weather.firstOrNull()?.main ?: ""

            val isGood = condMain !in listOf("Thunderstorm", "Tornado") &&
                    response.main.humidity < 90 &&
                    response.wind.speed < 15

            val advice = when {
                condMain == "Thunderstorm" -> "⚠️ Bahaya! Hindari inspeksi luar ruangan"
                condMain == "Tornado"      -> "🚨 Darurat! Tunda semua inspeksi lapangan"
                response.main.humidity > 85 -> "⚠️ Kelembaban tinggi, perhatikan keselamatan"
                response.wind.speed > 10   -> "⚠️ Angin kencang, hati-hati saat di ketinggian"
                condMain in listOf("Clear","Clouds") -> "✅ Kondisi baik untuk inspeksi lapangan"
                else                       -> "ℹ️ Pantau kondisi cuaca secara berkala"
            }

            return WeatherInfo(
                cityName = response.name,
                conditionMain = condMain,
                conditionDesc = desc,
                iconCode = icon,
                iconUrl = "https://openweathermap.org/img/wn/${icon}@2x.png",
                tempCelsius = String.format(Locale.US, "%.1f", tempC).toDouble(),
                feelsLikeCelsius = String.format(Locale.US, "%.1f", feelsLikeC).toDouble(),
                humidity = response.main.humidity,
                windSpeedMs = response.wind.speed,
                windSpeedKmh = String.format(Locale.US, "%.1f", windKmh).toDouble(),
                pressureHpa = response.main.pressure,
                cloudCoverPercent = response.clouds.all,
                visibilityKm = visKm,
                isGoodForInspection = isGood,
                inspectionAdvice = advice
            )
        }
    }
}
