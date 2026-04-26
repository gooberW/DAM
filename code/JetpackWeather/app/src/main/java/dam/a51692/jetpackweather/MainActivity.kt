package dam.a51692.jetpackweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dam.a51692.jetpackweather.ui.WeatherUI
import dam.a51692.jetpackweather.ui.theme.JetpackWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackWeatherTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WeatherUI()
                }
            }
        }
    }
}