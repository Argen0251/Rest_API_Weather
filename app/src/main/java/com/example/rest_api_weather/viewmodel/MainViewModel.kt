package com.example.rest_api_weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rest_api_weather.Repository.WeatherRepository
import com.example.rest_api_weather.model.models.WeatherRespons
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val repository = WeatherRepository()
    private val _weatherResponse = MutableLiveData<WeatherRespons>() //сохрaнять и читать
    val weatherResponse: LiveData<WeatherRespons> = _weatherResponse // чтение

    fun getWeather(location: String) {
        viewModelScope.launch {
            try {
                val respons = repository.getCurrentWeather(location)
                _weatherResponse.postValue(respons)
            } catch (e: Exception) {
                print(e.message)
            }
        }

    }

}