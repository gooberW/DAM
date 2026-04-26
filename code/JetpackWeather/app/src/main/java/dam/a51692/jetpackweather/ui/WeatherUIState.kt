package dam.a51692.jetpackweather.ui

data class WeatherUIState( // o default é lisvoa
    val latitude: Float = 38.7f,
    val longitude: Float = -9.1f,
    val temperature: Float = 0f,
    val windspeed: Float = 0f,
    val winddirection: Int = 0,
    val weathercode: Int = 0,
    val seaLevelPressure: Float = 0f,
    val time: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)
