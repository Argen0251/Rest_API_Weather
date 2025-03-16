package com.example.rest_api_weather.model.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
// Аннотация @Serializable указывает, что
// класс может быть преобразован в JSON и обратно
@Serializable
data class WeatherRespons(
    // @SerialName указывает, что поле "current"
    // в JSON соответствует переменной currentWeather в коде
    @SerialName("current")
    var current: Current? = null,
    @SerialName("location")
    var location: Location? = null
) {
    // Класс для текущей погоды
    @Serializable
    data class Current(
        @SerialName("cloud")
        var cloud: Int? = null,
        @SerialName("condition")
        var condition: Condition? = null,
        @SerialName("dewpoint_c")
        var dewpointC: Double? = null,
        @SerialName("dewpoint_f")
        var dewpointF: Double? = null,
        @SerialName("feelslike_c")
        var feelslikeC: Double? = null,
        @SerialName("feelslike_f")
        var feelslikeF: Double? = null,
        @SerialName("gust_kph")
        var gustKph: Double? = null,
        @SerialName("gust_mph")
        var gustMph: Double? = null,
        @SerialName("heatindex_c")
        var heatindexC: Double? = null,
        @SerialName("heatindex_f")
        var heatindexF: Double? = null,
        @SerialName("humidity")
        var humidity: Int? = null,
        @SerialName("is_day")
        var isDay: Int? = null,
        @SerialName("last_updated")
        var lastUpdated: String? = null,
        @SerialName("last_updated_epoch")
        var lastUpdatedEpoch: Int? = null,
        @SerialName("precip_in")
        var precipIn: Double? = null,
        @SerialName("precip_mm")
        var precipMm: Double? = null,
        @SerialName("pressure_in")
        var pressureIn: Double? = null,
        @SerialName("pressure_mb")
        var pressureMb: Double? = null,
        @SerialName("temp_c")
        var tempC: Double? = null,
        @SerialName("temp_f")
        var tempF: Double? = null,
        @SerialName("uv")
        var uv: Double? = null,
        @SerialName("vis_km")
        var visKm: Double? = null,
        @SerialName("vis_miles")
        var visMiles: Double? = null,
        @SerialName("wind_degree")
        var windDegree: Int? = null,
        @SerialName("wind_dir")
        var windDir: String? = null,
        @SerialName("wind_kph")
        var windKph: Double? = null,
        @SerialName("wind_mph")
        var windMph: Double? = null,
        @SerialName("windchill_c")
        var windchillC: Double? = null,
        @SerialName("windchill_f")
        var windchillF: Double? = null
    ) {
        // Класс для описания погодных условий
        @Serializable
        data class Condition(
            @SerialName("code")
            var code: Int? = null,
            @SerialName("icon")
            var icon: String? = null,
            @SerialName("text")
            var text: String? = null
        )
    }

    @Serializable
    data class Location(
        @SerialName("country")
        var country: String? = null,
        @SerialName("lat")
        var lat: Double? = null,
        @SerialName("localtime")
        var localtime: String? = null,
        @SerialName("localtime_epoch")
        var localtimeEpoch: Int? = null,
        @SerialName("lon")
        var lon: Double? = null,
        @SerialName("name")
        var name: String? = null,
        @SerialName("region")
        var region: String? = null,
        @SerialName("tz_id")
        var tzId: String? = null
    )
}