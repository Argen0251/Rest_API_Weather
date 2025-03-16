package com.example.rest_api_weather.Repository

import com.example.rest_api_weather.model.core.RetrofitClient
import com.example.rest_api_weather.model.models.WeatherRespons

class WeatherRepository {
    private val apiKey = "d3b4fb8a9c3c4f5bb64212320251403"
    suspend fun getCurrentWeather(location: String): WeatherRespons =
        RetrofitClient.retrofitService.getCurrentWeather(apiKey, location)

}