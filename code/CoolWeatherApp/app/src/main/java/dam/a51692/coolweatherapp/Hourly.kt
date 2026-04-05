package dam.a51692.coolweatherapp

data class Hourly (
    var time : ArrayList < String > ,
    var temperature_2m : ArrayList <Float > ,
    var weathercode : ArrayList <Int > ,
    var pressure_msl : ArrayList < Double >,
    var relativehumidity_2m: ArrayList<Int>
)
