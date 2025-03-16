package com.example.rest_api_weather.presenter

import com.example.rest_api_weather.model.models.WeatherRespons

interface WeatherContract {
    interface View {
        fun showWeather(weather: WeatherRespons)
        fun showError(message: String)
    }
    interface Presenter{
        fun loadData(location: String)
        fun onDestroy()
    }
}