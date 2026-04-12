package dam.a51692.coolweatherapp

data class CurrentWeather (
    var temperature : Float ,
    var windspeed : Float ,
    var winddirection : Int ,
    var weathercode : Int ,
    var time : String,
    val is_day: Int
)
