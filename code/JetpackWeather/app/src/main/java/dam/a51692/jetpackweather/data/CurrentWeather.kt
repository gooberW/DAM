package dam.a51692.jetpackweather.data

import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeather (
    var temperature : Float ,
    var windspeed : Float ,
    var winddirection : Int ,
    var weathercode : Int ,
    var time : String,
    val is_day: Int
)
