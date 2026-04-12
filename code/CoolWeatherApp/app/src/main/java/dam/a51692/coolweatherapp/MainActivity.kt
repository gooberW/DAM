package dam.a51692.coolweatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import dam.a51692.coolweatherapp.utils.WeatherUtils
import java.io.InputStreamReader
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var day: Boolean = true
    private var currentLat: Float? = null
    private var currentLon: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            day = savedInstanceState.getBoolean("day", true)
            currentLat = savedInstanceState.getFloat("lat")
            currentLon = savedInstanceState.getFloat("lon")
        }

        AppCompatDelegate.setDefaultNightMode(
            if (day)
                AppCompatDelegate.MODE_NIGHT_NO
            else
                AppCompatDelegate.MODE_NIGHT_YES
        )

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

            if (lat != null && lon != null) {
                currentLat = lat
                currentLon = lon

                curLocation.text = "$lat, $lon"
                fetchWeatherData(lat, lon)
            } else {
                Toast.makeText(this, R.string.invalid_coords, Toast.LENGTH_SHORT).show()
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // ✅ Decide what to load
        if (currentLat != null && currentLon != null && currentLat != 0f && currentLon != 0f) {
            fetchWeatherData(currentLat!!, currentLon!!)
            curLocation.text = "$currentLat, $currentLon"
        } else if (savedInstanceState == null) {
            setCurrentLocation()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("day", day)
        outState.putFloat("lat", currentLat ?: 0f)
        outState.putFloat("lon", currentLon ?: 0f)
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
            return Gson().fromJson(
                InputStreamReader(it, "UTF-8"),
                WeatherData::class.java
            )
        }
    }

    private fun fetchWeatherData(lat: Float, long: Float) {
        Thread {
            val weather = WeatherAPI_Call(lat, long)
            updateUI(weather)
        }.start()
    }

    private fun updateUI(request: WeatherData) {
        runOnUiThread {

            val newDay = request.current_weather.is_day == 1

            if (newDay != day) {
                day = newDay

                AppCompatDelegate.setDefaultNightMode(
                    if (day)
                        AppCompatDelegate.MODE_NIGHT_NO
                    else
                        AppCompatDelegate.MODE_NIGHT_YES
                )
            }

            val code = request.current_weather.weathercode

            val iconRes = WeatherUtils.getWeatherIcon(this, code)
            val bgRes = WeatherUtils.getWeatherBackground(this, code)

            findViewById<ImageView>(R.id.weatherIcon).setImageResource(iconRes)
            findViewById<ConstraintLayout>(R.id.main).setBackgroundResource(bgRes)

            val tempText = findViewById<TextView>(R.id.temperature)
            val windSpeedVal = findViewById<TextView>(R.id.windSpeedValue)
            val windDirVal = findViewById<TextView>(R.id.windDirValue)
            val humidityVal = findViewById<TextView>(R.id.humidityValue)
            val seaLvlPressureVal = findViewById<TextView>(R.id.seaLvlValue)

            val index = getCurrentHourIndex(request.hourly.time)

            tempText.text = "${request.current_weather.temperature.toInt()}º"
            windSpeedVal.text = "${request.current_weather.windspeed} km/h"
            windDirVal.text = "${request.current_weather.winddirection}°"
            humidityVal.text = "${request.hourly.relativehumidity_2m[index]}%"
            seaLvlPressureVal.text = "${request.hourly.pressure_msl[index]} hPa"
        }
    }

    private fun getCurrentHourIndex(hourlyTime: List<String>): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:00", Locale.getDefault())
        val currentTime = sdf.format(Date())
        val index = hourlyTime.indexOf(currentTime)
        return if (index != -1) index else 0
    }

    private fun setCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->

            if (location != null) {
                val lat = location.latitude.toFloat()
                val lon = location.longitude.toFloat()

                currentLat = lat
                currentLon = lon

                findViewById<TextView>(R.id.curLocation).text = "$lat, $lon"

                fetchWeatherData(lat, lon)
            } else {
                Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show()
            }
        }
    }
}