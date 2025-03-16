package com.example.rest_api_weather.model.service

import com.example.rest_api_weather.model.models.WeatherRespons
import retrofit2.http.GET
import retrofit2.http.Query

// Интерфейс, описывающий запросы к API
interface WeatherApiService {
    @GET("current.json")
    // Говорит, что мы делаем GET-запрос на сервер по адресу "current.json"

    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        // Добавляет параметр "key" в запрос (ключ для доступа к API)

        @Query("q") location: String
        // Добавляет параметр "q" в запрос (местоположение, например город)
    ): WeatherRespons
    // Функция вернёт данные о погоде в виде объекта WeatherResponse
}