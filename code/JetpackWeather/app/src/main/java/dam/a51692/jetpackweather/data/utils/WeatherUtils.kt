package dam.a51692.jetpackweather.data.utils

import android.content.Context
import androidx.compose.ui.graphics.Color
import dam.a51692.jetpackweather.R

object WeatherUtils {

    private fun getIndex(context: Context, code: Int): Int {
        val codes = context.resources.getStringArray(R.array.weather_codes)
        return codes.indexOf(code.toString())
    }

    fun getWeatherIcon(context: Context, code: Int): Int {
        val index = getIndex(context, code)
        if (index == -1) return R.drawable.overcast_icon

        val ta = context.resources.obtainTypedArray(R.array.weather_icons)
        val resId = ta.getResourceId(index, R.drawable.overcast_icon)
        ta.recycle()

        return resId
    }

    fun getWeatherBackground(context: Context, code: Int): List<Color> {
        val index = getIndex(context, code)
        return gradientForIndex(if (index == -1) 0 else index)
    }

    private fun gradientForIndex(index: Int): List<Color> {
        return when (index) {
            0 ->    listOf(Color(0xFF87CEEB), Color(0xFFFFD700)) // sunny
            1 ->    listOf(Color(0xFF87CEEB), Color(0xFFB0C4DE)) // partly cloudy
            2 ->    listOf(Color(0xFFB0C4DE), Color(0xFF708090)) // cloudy
            3 ->    listOf(Color(0xFF708090), Color(0xFF4A4A4A)) // overcast
            4 ->    listOf(Color(0xFF4A90D9), Color(0xFF1C3A5E)) // rain
            5 ->    listOf(Color(0xFF5B7FA6), Color(0xFF2C3E50)) // heavy rain
            6 ->    listOf(Color(0xFFB0BEC5), Color(0xFF607D8B)) // snow
            else -> listOf(Color(0xFF87CEEB), Color(0xFFB0C4DE)) // default
        }
    }

    fun getWeatherDescription(context: Context, code: Int): String {
        val index = getIndex(context, code)
        val desc = context.resources.getStringArray(R.array.weather_descriptions)
        return if (index != -1) desc[index] else "Unknown"
    }
}