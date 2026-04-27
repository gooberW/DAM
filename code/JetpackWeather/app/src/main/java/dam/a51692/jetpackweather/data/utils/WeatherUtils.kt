package dam.a51692.jetpackweather.data.utils

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import dam.a51692.jetpackweather.R

object WeatherUtils {

    private fun getIndex(context: Context, code: Int): Int {
        return try {
            val codes = context.resources.getStringArray(R.array.weather_codes)
            codes.map { it.trim() }.indexOf(code.toString())
        } catch (e: Exception) {
            -1
        }
    }

    fun getWeatherIcon(context: Context, code: Int): Int {
        val index = getIndex(context, code)
        val ta = context.resources.obtainTypedArray(R.array.weather_icons)

        val resId = if (index >= 0 && index < ta.length()) {
            ta.getResourceId(index, R.drawable.overcast_icon)
        } else {
            R.drawable.sunny_icon
        }

        ta.recycle()
        return resId
    }

    fun getWeatherDescription(context: Context, code: Int): String {
        val index = getIndex(context, code)
        val descriptions = context.resources.getStringArray(R.array.weather_descriptions)

        return if (index >= 0 && index < descriptions.size) {
            descriptions[index]
        } else {
            "Unknown ($code)"
        }
    }

    fun getWeatherBackground(context: Context, code: Int): List<Color> = when (code) {
        0, 1 -> listOf(Color(0xFF0068E7), Color(0xFF9ACFFF))
        2, 3 -> listOf(Color(0xFF87CEEB), Color(0xFFB0C4DE))
        45, 48 -> listOf(Color(0xFF708090), Color(0xFF4A4A4A))
        in 51..67, in 80..82 -> listOf(Color(0xFF4A90D9), Color(0xFF1C3A5E))
        in 71..77, in 85..86 -> listOf(Color(0xFFB0BEC5), Color(0xFF607D8B))
        else -> listOf(Color(0xFF5B7FA6), Color(0xFF2C3E50))
    }
}