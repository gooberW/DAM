package dam.a51692.jetpackweather.data

import kotlinx.serialization.Serializable

@Serializable
data class WeatherData (
    var latitude : Double ,
    var longitude : Double ,
    var timezone : String ,
    var current_weather : CurrentWeather ,
    var hourly : Hourly
)
