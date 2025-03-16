package com.example.rest_api_weather.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rest_api_weather.R
import com.example.rest_api_weather.databinding.ActivityMainBinding
import com.example.rest_api_weather.model.models.WeatherRespons
import com.example.rest_api_weather.presenter.WeatherContract
import com.example.rest_api_weather.presenter.WeatherPresenter
// MainActivity — это главный экран приложения, который показывает данные о погоде
// Он реализует интерфейс WeatherContract.View, что значит: MainActivity знает, как отображать данные и ошибки
class MainActivity : AppCompatActivity(), WeatherContract.View {

    // binding нужен для работы с элементами интерфейса (например, textView), чтобы не писать findViewById
    private lateinit var binding: ActivityMainBinding

    // presenter отвечает за логику загрузки данных о погоде
    // by lazy означает, что presenter создаётся только тогда, когда он впервые понадобится
    private val presenter by lazy { WeatherPresenter(this) }

    // Функция, которая вызывается при создании экрана
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Подключаем макет экрана с помощью View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListener()

    }
    fun setupClickListener(){
        //spinner
        val cities = listOf("Bishkek", "London", "Dubai", "Tokyo", "Paris","Moscow")
        presenter.loadData("Bishkek")
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
                presenter.loadData( city)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    // Функция для отображения данных о погоде на экране
    // Этот метод вызывается, когда presenter успешно получил данные с сервера
    override fun showWeather(weatherResponse: WeatherRespons) {
        binding.apply {
            // Устанавливаем текст в textView (температура из ответа сервера)
            textView.text = "${weatherResponse.current?.tempC.toString()}°"
                //осадки
            val condition = weatherResponse.current?.condition?.text ?: ""
            Toast.makeText(this@MainActivity, "Condition: $condition", Toast.LENGTH_SHORT).show()
            val imageRes = when {
                condition.contains("sun") || condition.contains("clear") -> R.drawable.ic_sun
                condition.contains("cloud") || condition.contains("partly")|| condition.contains("drizzle") -> R.drawable.ic_cloud
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
        }
        //показатели осадки, влажности, ветра
        binding.osadki.text = weatherResponse.current?.condition?.text ?: ""
        binding.rainPre.text = "${weatherResponse.current?.precipMm.toString()}%"
        binding.humidity.text = "${weatherResponse.current?.humidity.toString()}%"
        binding.wind.text = "${weatherResponse.current?.windKph.toString()}km/h"
    }

    // Функция для отображения ошибки, если что-то пошло не так (например, нет интернета)
    override fun showError(message: String) {
        // Показываем всплывающее сообщение с ошибкой
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Функция вызывается, когда экран закрывается
    // Здесь мы очищаем ресурсы, чтобы не было утечек памяти
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}