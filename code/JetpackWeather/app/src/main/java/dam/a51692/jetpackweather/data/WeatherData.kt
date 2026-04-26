package dam.a51692.jetpackweather.data

import kotlinx.serialization.Serializable

@Serializable
data class WeatherData (
    var latitude : String ,
    var longitude : String ,
    var timezone : String ,
    var current_weather : CurrentWeather ,
    var hourly : Hourly
)
