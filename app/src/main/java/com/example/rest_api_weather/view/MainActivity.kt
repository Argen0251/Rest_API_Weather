package com.example.rest_api_weather.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rest_api_weather.R
import com.example.rest_api_weather.databinding.ActivityMainBinding
import com.example.rest_api_weather.viewmodel.MainViewModel

// MainActivity — это главный экран приложения, который показывает данные о погоде
// Он реализует интерфейс WeatherContract.View, что значит: MainActivity знает, как отображать данные и ошибки
class MainActivity : AppCompatActivity(){

    // binding нужен для работы с элементами интерфейса (например, textView), чтобы не писать findViewById
    private lateinit var binding: ActivityMainBinding

     private val viewModel:MainViewModel by viewModels()

    // Функция, которая вызывается при создании экрана
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Подключаем макет экрана с помощью View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListener()
        initialize()
    }

    private fun initialize() {
        binding.apply {
            viewModel.weatherResponse.observe(this@MainActivity) {response->
                // Устанавливаем текст в textView (температура из ответа сервера)
                textView.text = "${response.current?.tempC.toString()}°"
                //осадки
                val condition = response.current?.condition?.text ?: ""
                Toast.makeText(this@MainActivity, "Condition: $condition", Toast.LENGTH_SHORT)
                    .show()
                val imageRes = when {
                    condition.contains("sun") || condition.contains("clear") -> R.drawable.ic_sun
                    condition.contains("cloud") || condition.contains("partly") || condition.contains(
                        "drizzle"
                    ) -> R.drawable.ic_cloud

                    condition.contains("rain") -> R.drawable.ic_cloud
                    else -> R.drawable.ic_sun
                }
                imgWeather.setImageResource(imageRes)
                //фон
                val cond = if (condition.contains("clear") || condition.contains("sun")) {
                    R.drawable.color_light_bacground
                } else {
                    R.drawable.color_bacgr
                }
                main.setBackgroundResource(cond)
                //показатели осадки, влажности, ветра
                binding.osadki.text =
                    response.current?.condition?.text ?: ""
                binding.rainPre.text =
                    "${response.current?.precipMm.toString()}%"
                binding.humidity.text =
                    "${response.current?.humidity.toString()}%"
                binding.wind.text =
                    "${response.current?.windKph.toString()}km/h"
            }
        }}

        fun setupClickListener() {
            //spinner
            val cities = listOf("Bishkek", "London", "Dubai", "Tokyo", "Paris", "Moscow")
            viewModel.getWeather("Bishkek")
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cities
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter

            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val city = cities[position]
                    viewModel.getWeather(city)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }

    }