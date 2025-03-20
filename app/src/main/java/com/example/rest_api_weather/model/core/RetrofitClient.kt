package com.example.rest_api_weather.model.core

import com.example.rest_api_weather.model.models.WeatherRespons
import com.example.rest_api_weather.model.service.WeatherApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

// Объект для настройки и работы с сетевыми запросами через Retrofit
object RetrofitClient {
    // Это базовый адрес сервера, к которому будем отправлять запросы
    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    // Создаём Interceptor для логирования запросов и ответов
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
        // level = BODY означает, что будет записываться ВСЁ: и запросы, и ответы, включая их содержимое
    }

    // Создаём HTTP-клиент, который будет отправлять запросы на сервер
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        // Добавляем Interceptor в HTTP-клиент. Он будет "перехватывать" запросы и записывать их в лог
        .build()
    // Строим (создаём) готовый HTTP-клиент

    // Настраиваем работу с JSON (формат данных, который возвращает сервер)
    private val json = Json {
        ignoreUnknownKeys = true
        // Если сервер пришлёт данные, которых нет в нашем классе, они будут проигнорированы, и приложение не сломается

        isLenient = true
        // Это настройка для "прощения" ошибок в данных (например, если данные слегка отличаются от стандарта)
    }

    @OptIn(ExperimentalSerializationApi::class)
    val retrofitService:  WeatherApiService by lazy {
        Retrofit.Builder()
            // Создаём объект Retrofit для отправки запросов
            .baseUrl(BASE_URL)
            // Устанавливаем базовый адрес сервера
            .client(httpClient)
            // Подключаем наш HTTP-клиент с Interceptor для логирования
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            // Добавляем конвертер, который будет преобразовывать JSON-ответы в объекты Kotlin
            .build()
            // Создаём полностью настроенный объект Retrofit
            .create(WeatherApiService::class.java)
        // Говорим Retrofit, какой интерфейс использовать для запросов (описанный в WeatherApiService)
    }
}
