package dam.a51692.coolweatherapp

data class WeatherData (
    var latitude : String ,
    var longitude : String ,
    var timezone : String ,
    var current_weather : CurrentWeather ,
    var hourly : Hourly
)
