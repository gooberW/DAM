package dam.a51692.jetpackweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a51692.jetpackweather.data.WeatherApiClient
import dam.a51692.jetpackweather.ui.WeatherUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherUIState())
    val uiState: StateFlow<WeatherUIState> = _uiState.asStateFlow()

    init {
        fetchWeather()
    }

    fun updateLatitude(latitude: Float) {
        _uiState.update { it.copy(latitude = latitude) }
    }

    fun updateLongitude(longitude: Float) {
        _uiState.update { it.copy(longitude = longitude) }
    }

    fun fetchWeather() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val weather = WeatherApiClient.getWeather(
                    lat = _uiState.value.latitude,
                    lon = _uiState.value.longitude,
                )
                if (weather != null) {
                    _uiState.update {
                        it.copy(
                            temperature = weather.current_weather.temperature,
                            windspeed = weather.current_weather.windspeed,
                            winddirection = weather.current_weather.winddirection,
                            weathercode = weather.current_weather.weathercode,
                            seaLevelPressure = weather.hourly.pressure_msl[0].toFloat(),
                            time = weather.current_weather.time,
                            isLoading = false,
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Failed to fetch weather") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}