package com.example.rest_api_weather.presenter

import com.example.rest_api_weather.Repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

// Класс, который управляет логикой загрузки данных о погоде
class WeatherPresenter(private var view: WeatherContract.View?) : WeatherContract.Presenter {

    // Репозиторий — это класс, который отвечает за получение данных из интернета
    private val repository = WeatherRepository()

    // Job — это контроллер для корутин. Он помогает управлять жизненным циклом задач.
    // Если вызвать job.cancel(), все связанные с ним корутины остановятся.
    // Подробнее: https://kotlinlang.org/docs/coroutines-basics.html#job
    private val job = Job()

    // CoroutineScope — это область (контекст), в которой запускаются корутины.
    // Она указывает, где и как будут выполняться задачи.
    // Подробнее: https://kotlinlang.org/docs/coroutines-basics.html#coroutine-scope
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /*
     * Что такое Coroutine (Корутина)?
     * Корутина — это лёгкий поток для выполнения асинхронных задач.
     * Это как параллельная линия работы, которая позволяет выполнять задачи (например, загрузка данных)
     * без остановки основного приложения.
     * Подробнее: https://kotlinlang.org/docs/coroutines-basics.html
     *
     * Что такое Dispatcher?
     * Dispatcher определяет, в каком потоке будет выполняться корутина.
     * - Dispatchers.Main: выполняет задачи в главном (UI) потоке, где происходит взаимодействие с интерфейсом.
     * - Dispatchers.IO: используется для операций ввода-вывода (чтение файлов, запросы в интернет).
     * - Dispatchers.Default: для тяжёлых вычислений.
     * Подробнее: https://kotlinlang.org/docs/coroutines-basics.html#dispatchers-and-threads
     */

    // Функция для загрузки данных о погоде по указанному местоположению
    override fun loadData(location: String) {
        // Запускаем корутину в scope (область выполнения)
        scope.launch {
            try {
                // Вызов функции из репозитория для получения данных о погоде
                // Эта операция выполняется асинхронно (не блокирует интерфейс)
                val weatherResponse = repository.getCurrentWeather(location)

                // Если данные успешно получены, вызываем метод отображения погоды
                view?.showWeather(weatherResponse)
            } catch (e: Exception) {
                // Если произошла ошибка (например, нет интернета), показываем сообщение об ошибке
                view?.showError(e.message ?: "Unknown error") // Если ошибки нет, выводим "Unknown error"
            }
        }
    }

    // Функция вызывается при уничтожении Presenter (например, когда закрывается экран)
    override fun onDestroy() {
        view = null // Убираем ссылку на View, чтобы избежать утечек памяти

        job.cancel() // Отменяем все корутины, которые были запущены в этом Presenter
        // Это нужно, чтобы не продолжать работу, если экран уже закрыт
    }
}