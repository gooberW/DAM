package dam.a51692.jetpackweather.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dam.a51692.jetpackweather.R
import dam.a51692.jetpackweather.data.utils.WeatherUtils
import dam.a51692.jetpackweather.ui.theme.JetpackWeatherTheme
import dam.a51692.jetpackweather.viewmodel.WeatherViewModel

@Composable
fun WeatherUI(weatherViewModel: WeatherViewModel = viewModel()) {
    val weatherUIState by weatherViewModel.uiState.collectAsState()

    var localLat by rememberSaveable { mutableStateOf(weatherUIState.latitude.toString()) }
    var localLon by rememberSaveable { mutableStateOf(weatherUIState.longitude.toString()) }

    val latitude = weatherUIState.latitude
    val longitude = weatherUIState.longitude
    val temperature = weatherUIState.temperature
    val windSpeed = weatherUIState.windspeed
    val windDirection = weatherUIState.winddirection
    val weathercode = weatherUIState.weathercode
    val seaLevelPressure = weatherUIState.seaLevelPressure
    val humidity = weatherUIState.humidity
    val time = weatherUIState.time
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val wIcon = WeatherUtils.getWeatherIcon(context, weathercode)
    val wBackground: List<Color> = WeatherUtils.getWeatherBackground(context, weathercode)
    val wDescription = WeatherUtils.getWeatherDescription(context, weathercode)

    val sharedProps = WeatherUIProps(
        wIcon = wIcon,
        wBackground = wBackground,
        wDescription = wDescription,
        latitude = latitude,
        longitude = longitude,
        temperature = temperature,
        windSpeed = windSpeed,
        windDirection = windDirection,
        weathercode = weathercode,
        seaLevelPressure = seaLevelPressure,
        humidity = humidity,
        time = time,
        latitudeText = localLat, // texto local
        longitudeText = localLon, // texto local
        onLatitudeChange = { localLat = it }, // apenas o buffer local
        onLongitudeChange = { localLon = it }, // apenas o buffer local
        onUpdateButtonClick = {
            // envia para o ViewModel
            localLat.toFloatOrNull()?.let { weatherViewModel.updateLatitude(it) }
            localLon.toFloatOrNull()?.let { weatherViewModel.updateLongitude(it) }
            weatherViewModel.fetchWeather()
        }
    )

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeWeatherUI(sharedProps)
    } else {
        PortraitWeatherUI(sharedProps)
    }
}

data class WeatherUIProps(
    val wIcon: Int,
    val wBackground: List<Color>,
    val wDescription: String,
    val latitude: Float,
    val longitude: Float,
    val temperature: Float,
    val windSpeed: Float,
    val windDirection: Int,
    val weathercode: Int,
    val seaLevelPressure: Float,
    val humidity: Float,
    val time: String,
    val onLatitudeChange: (String) -> Unit,
    val onLongitudeChange: (String) -> Unit,
    val onUpdateButtonClick: () -> Unit,
    val latitudeText: String,
    val longitudeText: String,
)

@Composable
fun PortraitWeatherUI(props: WeatherUIProps) {
    val gradient = Brush.verticalGradient(props.wBackground)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 20.dp)
    ) {
        LocationCard(props.latitude.toDouble(), props.longitude.toDouble())
        Spacer(modifier = Modifier.height(30.dp))
        TemperatureCard(props.temperature, props.wIcon, props.wDescription)
        Spacer(Modifier.height(10.dp))
        CoordsCard(
            latitude = props.latitudeText,
            longitude = props.longitudeText,
            onLatitudeChange = props.onLatitudeChange,
            onLongitudeChange = props.onLongitudeChange
        )
        Spacer(Modifier.height(10.dp))
        InfoGrid(
            items = listOf(
                Triple("Wind Speed", props.windSpeed, "km/h"),
                Triple("Wind Direction", props.windDirection.toFloat(), "°"),
                Triple("Pressure", props.seaLevelPressure, "hPa"),
                Triple("Humidity", props.humidity, "%"),
            )
        )
        Spacer(Modifier.weight(1f))
        UpdateButton(onClick = props.onUpdateButtonClick)
    }
}

@Composable
fun LandscapeWeatherUI(props: WeatherUIProps) {
    val gradient = Brush.verticalGradient(props.wBackground)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TemperatureCard(props.temperature, props.wIcon, props.wDescription)
            Spacer(Modifier.height(10.dp))
            LocationCard(props.latitude.toDouble(), props.longitude.toDouble())
        }

        Spacer(Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoordsCard(
                latitude = props.latitudeText,
                longitude = props.longitudeText,
                onLatitudeChange = props.onLatitudeChange,
                onLongitudeChange = props.onLongitudeChange
            )
            Spacer(Modifier.height(10.dp))
            InfoGrid(
                items = listOf(
                    Triple("Wind Speed", props.windSpeed, "km/h"),
                    Triple("Direction", props.windDirection.toFloat(), "°"),
                    Triple("Pressure", props.seaLevelPressure, "hPa"),
                    Triple("Humidity", props.humidity, "%"),
                )
            )
            Spacer(Modifier.weight(1f))
            UpdateButton(onClick = props.onUpdateButtonClick)
        }
    }
}

@Composable
fun CoordsCard(
    latitude: String,
    longitude: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Coordinates",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = latitude,
            onValueChange = onLatitudeChange,
            label = { Text("Latitude") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = longitude,
            onValueChange = onLongitudeChange,
            label = { Text("Longitude") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

@Composable
fun UpdateButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = "Update", fontSize = 14.sp)
    }
}

@Composable
@Preview
fun Preview() {
    JetpackWeatherTheme {
        PortraitWeatherUI(
            props = WeatherUIProps(
                wIcon = R.drawable.sunny_icon,
                wBackground = listOf(Color(0xFF1976D2), Color(0xFF90CAF9)),
                wDescription = "Sunny",
                latitude = 20f,
                longitude = -130f,
                temperature = 20f,
                windSpeed = 12f,
                windDirection = 180,
                weathercode = 0,
                seaLevelPressure = 1013f,
                humidity = 10f,
                time = "12:00",
                latitudeText = "",
                longitudeText = "",
                onLatitudeChange = {},
                onLongitudeChange = {},
                onUpdateButtonClick = {},
            )
        )
    }
}

@Composable
fun TemperatureCard(temperature: Float, wIcon: Int, wDescription: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth().padding(bottom = 20.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(wIcon),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(80 .dp)
        )
        Text(
            text = "${temperature.toInt()}°",
            fontSize = 120.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Text(
            text = wDescription,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun InfoCard(title: String, value: Float, unit: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text (
            text = title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimary

        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "${value} ${unit}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun InfoGrid(items: List<Triple<String, Float, String>>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(items) { (title, value, unit) ->
            InfoCard(title, value, unit)
        }
    }
}

@Composable
fun LocationCard(lat: Double, lon : Double) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
    ) {
        Text(text = "Current Location:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "${lat}, ${lon}",
            color = MaterialTheme.colorScheme.onPrimary)
    }
}