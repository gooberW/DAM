package dam.a51692.coolweatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.InputStreamReader
import java.net.URL
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val latInput = findViewById<EditText>(R.id.latitudeInput)
        val lonInput = findViewById<EditText>(R.id.longitudeInput)
        val updateBtn = findViewById<Button>(R.id.updateBtn)
        val curLocation = findViewById<TextView>(R.id.curLocation)

        updateBtn.setOnClickListener {
            val lat = latInput.text.toString().toFloatOrNull()
            val lon = lonInput.text.toString().toFloatOrNull()

            curLocation.text = "${lat}, ${lon}"

            if (lat != null && lon != null) {
                fetchWeatherData(lat, lon)
            } else {
                Toast.makeText(this,
                    "Invalid coordinates", Toast.LENGTH_SHORT).show()
            }
        }

        // lat e lon aleatorias quando entras na app pela 1a vez

        val rndLat = Random.nextDouble(-90.0, 90.0).toFloat()
        val rndLon = Random.nextDouble(-180.0, 180.0).toFloat()
        curLocation.text = "${rndLat}, ${rndLon}"

        fetchWeatherData(rndLat, rndLon)


    }

    private fun WeatherAPI_Call(lat: Float, long: Float): WeatherData {
        val reqString = buildString {
            append("https://api.open-meteo.com/v1/forecast?")
            append("latitude=$lat&longitude=$long&")
            append("current_weather=true&")
            append("hourly=temperature_2m,weathercode,pressure_msl,windspeed_10m,relativehumidity_2m")
        }

        val url = URL(reqString)
        url.openStream().use {
            val request = Gson().fromJson(InputStreamReader(it, "UTF-8"),
                WeatherData::class.java)
            return request
        }
    }

    private fun fetchWeatherData (lat: Float, long : Float) {
        return Thread {
            val weather = WeatherAPI_Call (lat, long)
            updateUI(weather)
        }.start()
    }

    private fun updateUI ( request : WeatherData ) {
        runOnUiThread {
            //images/icons
            val weatherIcon = findViewById<ImageView>(R.id.weatherIcon)
            val main = findViewById<ConstraintLayout>(R.id.main);

            var newIconID = getWeatherIconResource(request.current_weather.weathercode)
            var newIcon = getDrawable(newIconID)
            weatherIcon.setImageDrawable(newIcon)

            if(newIconID != R.drawable.sunny_icon) {
                main.setBackgroundResource(R.drawable.overcast_sky_day)
            } else {
                main.setBackgroundResource(R.drawable.clear_sky_day)
            }

            //info
            val tempText = findViewById<TextView>(R.id.temperature)
            val windSpeedVal = findViewById<TextView>(R.id.windSpeedValue)
            val windDirVal = findViewById<TextView>(R.id.windDirValue)
            val humidityVal = findViewById<TextView>(R.id.humidityValue)
            val seaLvlPressureVal = findViewById<TextView>(R.id.seaLvlValue)

            var index = getCurrentHourIndex(request.hourly.time)

            tempText.text = "${request.current_weather.temperature}º"
            windSpeedVal.text = "${request.current_weather.windspeed} km/h"
            windDirVal.text = "${request.current_weather.winddirection}°"
            humidityVal.text = "${request.hourly.relativehumidity_2m[index]}%"
            seaLvlPressureVal.text = "${request.hourly.pressure_msl[index]} hPa"

        }
    }

    fun getWeatherIconResource(code: Int, isDay: Boolean = true): Int {
        val weatherMap = getWeatherCodeMap()
        val wCode = weatherMap[code] ?: WMO_WeatherCode.CLEAR_SKY

        return when (wCode) {
            WMO_WeatherCode.CLEAR_SKY,
            WMO_WeatherCode.MAINLY_CLEAR,
            WMO_WeatherCode.PARTLY_CLOUDY -> R.drawable.sunny_icon

            WMO_WeatherCode.OVERCAST,
            WMO_WeatherCode.FOG,
            WMO_WeatherCode.DEPOSITING_RIME_FOG -> R.drawable.overcast_icon

            WMO_WeatherCode.DRIZZLE_LIGHT,
            WMO_WeatherCode.DRIZZLE_MODERATE,
            WMO_WeatherCode.DRIZZLE_DENSE,
            WMO_WeatherCode.RAIN_SLIGHT,
            WMO_WeatherCode.RAIN_MODERATE,
            WMO_WeatherCode.RAIN_HEAVY,
            WMO_WeatherCode.RAIN_SHOWERS_SLIGHT,
            WMO_WeatherCode.RAIN_SHOWERS_MODERATE,
            WMO_WeatherCode.RAIN_SHOWERS_VIOLENT -> R.drawable.rain_icon

            WMO_WeatherCode.FREEZING_DRIZZLE_LIGHT,
            WMO_WeatherCode.FREEZING_DRIZZLE_DENSE,
            WMO_WeatherCode.FREEZING_RAIN_LIGHT,
            WMO_WeatherCode.FREEZING_RAIN_HEAVY,
            WMO_WeatherCode.SNOW_FALL_SLIGHT,
            WMO_WeatherCode.SNOW_FALL_MODERATE,
            WMO_WeatherCode.SNOW_FALL_HEAVY,
            WMO_WeatherCode.SNOW_GRAINS,
            WMO_WeatherCode.SNOW_SHOWERS_SLIGHT,
            WMO_WeatherCode.SNOW_SHOWERS_HEAVY -> R.drawable.freezing_icon

            WMO_WeatherCode.THUNDERSTORM_SLIGHT_MODERATE,
            WMO_WeatherCode.THUNDERSTORM_HAIL_SLIGHT,
            WMO_WeatherCode.THUNDERSTORM_HAIL_HEAVY -> R.drawable.thunder_icon
        }
    }

    fun getCurrentHourIndex(hourlyTime: List<String>): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:00", Locale.getDefault())
        val currentTime = sdf.format(Date())

        val index = hourlyTime.indexOf(currentTime)

        return if (index != -1) index else 0 // fallback if not found
    }
}